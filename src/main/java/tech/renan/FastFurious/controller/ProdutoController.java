package tech.renan.FastFurious.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tech.renan.FastFurious.domain.model.Produto;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private List<Produto> produtos = new ArrayList<>();
    private Long contadorId = 1L;

    // GET /produto
    @GetMapping
    public List<Produto> listar() {
        return produtos;
    }

    // GET /produto/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = produtos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        return produto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /produto
    @PostMapping
    public Produto adicionar(@RequestBody Produto produto) {
        produto.setId(contadorId++);
        produtos.add(produto);
        return produto;
    }

    // PUT /produto/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto novoProduto) {
        for (Produto p : produtos) {
            if (p.getId().equals(id)) {
                p.setNome(novoProduto.getNome());
                p.setValor(novoProduto.getValor());
                p.setCategoria(novoProduto.getCategoria());
                return ResponseEntity.ok(p);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /produto/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean removido = produtos.removeIf(p -> p.getId().equals(id));
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // GET /produto/cat/{categoria}
    @GetMapping("/cat/{categoria}")
    public List<Produto> listarPorCategoria(@PathVariable String categoria) {
        List<Produto> resultado = new ArrayList<>();
        for (Produto p : produtos) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}