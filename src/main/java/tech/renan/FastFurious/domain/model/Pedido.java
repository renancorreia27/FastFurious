package tech.renan.FastFurious.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Aluno
 */

@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;
    
               
    private int quantidade;
    private double total;
    
    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.ABERTO;

    public Pedido() {
    }

    public Pedido(Long id, List<ItemPedido> itens, int quantidade, double total, StatusPedido status) {
        this.id = id;
        this.itens = itens;
        this.quantidade = quantidade;
        this.total = total;
        this.status = status;
    }

    
   public void calcularTudo() {
        this.total = this.itens.stream()
            // Muda o formato para Double e pega o total de cada item
            .mapToDouble(ItemPedido::getTotal)
            .sum();

        // Atualiza outros campos se necessário
        this.quantidade = this.itens.stream() 
            // Muda o formato para Int e pega a quantidade de cada item
            .mapToInt(ItemPedido::getQuantidade)
            .sum();
    }
  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }


    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pedido other = (Pedido) obj;
        return Objects.equals(this.id, other.id);
    }
    
}