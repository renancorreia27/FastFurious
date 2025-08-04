package tech.renan.FastFurious.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import tech.renan.FastFurious.domain.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByCategoriaIgnoreCase(String categoria);
}