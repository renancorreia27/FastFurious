package tech.renan.FastFurious.controller;

import java.util.Arrays;
import java.util.Optional;
import tech.renan.Fast_and_Furious_Food.domain.model.Pedido;
import tech.renan.Fast_and_Furious_Food.domain.model.StatusPedido;
import tech.renan.Fast_and_Furious_Food.domain.repository.PedidoRepository;
import tech.renan.Fast_and_Furious_Food.domain.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Renan
 */

@RestController
@RequestMapping("/fastfurious/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    private PedidoRepository pedidoRepository;

   @PostMapping
    public ResponseEntity<?> criar(@RequestBody Pedido itemPedido) {
        try {
            Pedido pedidoSalvo = pedidoService.criar(itemPedido);
            return ResponseEntity.ok(pedidoSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscar (@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        
        if(pedido.isPresent()){
            return ResponseEntity.ok(pedido.get());
        } else {
           return ResponseEntity.notFound().build();
        }
    
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<Pedido> buscar (@PathVariable StatusPedido status) {
        Optional<Pedido> pedido = pedidoRepository.findByStatus(status);
        
        if(pedido.isPresent()){
            return ResponseEntity.ok(pedido.get());
        } else {
           return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir (@PathVariable Long id){
        
        if(!pedidoRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        } else {
            pedidoService.excluir(id);
            return ResponseEntity.noContent().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarId (@PathVariable Long id,
                                               @RequestBody Pedido pedido){
    
        if(!pedidoRepository.existsById(id)){
           return ResponseEntity.notFound().build();
        } else{
            pedido.setId(id);
            pedido = pedidoService.salvar(pedido);
         
            return ResponseEntity.ok(pedido);
        
        }
    }

    @PutMapping(value = "/status/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pedido> atualizar (@PathVariable Long id,
                                             @RequestBody Pedido pedido) {
       
        if(!pedidoRepository.existsById(id)){
           return ResponseEntity.notFound().build();
        } else {
           pedido.setId(id);
           
           if (pedido.getStatus() == null || !statusValido(pedido.getStatus())) {
                throw new IllegalArgumentException("Status inválido");
            }
                    
           pedido = pedidoService.salvar(pedido);
           return ResponseEntity.ok(pedido);
        }
    }
    
    private boolean statusValido(StatusPedido status) {
        return Arrays.asList(StatusPedido.values()).contains(status);
    }
       
}
