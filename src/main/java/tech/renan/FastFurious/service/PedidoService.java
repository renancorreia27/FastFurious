package tech.renan.FastFurious.service;

import jakarta.transaction.Transactional;
import java.util.List;
import tech.renan.Fast_and_Furious_Food.domain.model.ItemPedido;
import tech.renan.Fast_and_Furious_Food.domain.model.Pedido;
import tech.renan.Fast_and_Furious_Food.domain.model.Produto;
import tech.renan.Fast_and_Furious_Food.domain.model.StatusPedido;
import tech.renan.Fast_and_Furious_Food.domain.repository.PedidoRepository;
import tech.renan.Fast_and_Furious_Food.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Renan
 */
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }    
    
    @Transactional
    public Pedido criar(Pedido pedido) {
        pedido.setStatus(StatusPedido.ABERTO);

        for (ItemPedido item : pedido.getItens()) {
            Produto produto = produtoRepository.findById(item.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            item.setStatus(StatusPedido.ABERTO);
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPreco(produto.getPreco());
            item.setTotal(produto.getPreco() * item.getQuantidade());
        }
        
        pedido.getItens().forEach(item -> {
        item.calcularTotal();
        });
        
        pedido.calcularTudo();

        return pedidoRepository.save(pedido);
    }
    
    public void excluir(Long id){
       pedidoRepository.deleteById(id);
        
    }
    
    public Pedido salvar(Pedido pedido) {
    return pedidoRepository.save(pedido);
    }
}
