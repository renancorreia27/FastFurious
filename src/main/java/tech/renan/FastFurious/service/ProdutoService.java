package tech.renan.FastFurious.service;

import org.springframework.stereotype.Service;
import tech.renan.FastFurious.domain.model.Produto;
import tech.renan.FastFurious.repository.ProdutoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    
    public List<Produto> findByCategoria(String categoria) {
        return produtoRepository.findByCategoriaIgnoreCase(categoria);
    }
    
    public void excluir(long id) {
        produtoRepository.deleteById(id);
    }
    
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
        
    }
}