package tech.renan.FastFurious.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.renan.FastFurious.domain.model.Pedido;
import tech.renan.FastFurious.domain.model.StatusPedido;

/**
 *
 * @author Aluno
 */

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
   /**
    * Responsável por buscar os pedidos com um Status em específico.
    * @param status Status dos pedidos
    * @return 
    */
    Optional<Pedido> findByStatus (StatusPedido status);
}