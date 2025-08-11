package tech.renan.FastFurious.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.renan.FastFurious.domain.model.Pedido;
import tech.renan.FastFurious.domain.model.StatusPedido;

/**
 *
 * @author Renan
 */

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByStatus (StatusPedido status);
}
