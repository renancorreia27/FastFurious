package tech.renan.FastFurious.controller;

import java.util.Arrays;
import java.util.Optional;
import local.enrico.Fast_and_Furious_Food.domain.model.Pedido;
import local.enrico.Fast_and_Furious_Food.domain.model.StatusPedido;
import local.enrico.Fast_and_Furious_Food.domain.repository.PedidoRepository;
import local.enrico.Fast_and_Furious_Food.domain.service.PedidoService;
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

   //=============================POST=======================================//
   @PostMapping
   // O '?' indica que pode ser qualquer campo no 'ResponseEntity'
    public ResponseEntity<?> criar(@RequestBody Pedido itemPedido) {
        try {
            Pedido pedidoSalvo = pedidoService.criar(itemPedido);
            return ResponseEntity.ok(pedidoSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    //==============================GET=======================================//
    
    //=======ID=========//
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscar (@PathVariable Long id) {
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        
        if(pedido.isPresent()){
            return ResponseEntity.ok(pedido.get());
        } else {
           return ResponseEntity.notFound().build();
        }
    
    }
    
    //====STATUS====//
    @GetMapping("/status/{status}")
    public ResponseEntity<Pedido> buscar (@PathVariable StatusPedido status) {
        Optional<Pedido> pedido = pedidoRepository.findByStatus(status);
        
        if(pedido.isPresent()){
            return ResponseEntity.ok(pedido.get());
        } else {
           return ResponseEntity.notFound().build();
        }
    }
    
    //===========================DELETE=======================================//
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir (@PathVariable Long id){
        
        // Verifica se existe ou não o código e exclui caso positivo.
        if(!pedidoRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        } else {
            pedidoService.excluir(id);
            return ResponseEntity.noContent().build();
        }
    }
    
    //==============================PUT=======================================//
    
    //=======ID=======//
    
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
    //=====STATUS=====//
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
    
    /**
     * Aqui foi criado essa classe para a verificação se existe ou não o Status 
     * informado pelo usuário
     * @param status Remete ao Status do pedido
     * @return Retorna uma lista de todos os Status
     */
    private boolean statusValido(StatusPedido status) {
        return Arrays.asList(StatusPedido.values()).contains(status);
    }
    
    

    
}
