package tech.renan.FastFurious.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.renan.FastFurious.domain.model.Produto;
import tech.renan.FastFurious.repository.ProdutoRepository;
import tech.renan.FastFurious.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/fastfurious/produto")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoService produtoService, ProdutoRepository produtoRepository) {
        this.produtoService = produtoService;
        this.produtoRepository = produtoRepository;
    }

    // GET /fastfurious/produto
    @GetMapping
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    // GET /fastfurious/produto/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {  
        return produtoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /fastfurious/produto
    @PostMapping
    public Produto adicionar(@RequestBody Produto produto) {
        return produtoService.salvar(produto);
    }

    // PUT /fastfurious/produto/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto novoProduto) {
        return produtoRepository.findById(id)
                .map(p -> {
                    p.setNome(novoProduto.getNome());
                    p.setValor(novoProduto.getValor());
                    p.setCategoria(novoProduto.getCategoria());
                    return ResponseEntity.ok(produtoRepository.save(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /fastfurious/produto/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (produtoRepository.existsById(id)) {
            produtoService.excluir(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET /fastfurious/produto/cat/{categoria}
    @GetMapping("/cat/{categoria}")
    public List<Produto> listarPorCategoria(@PathVariable String categoria) {
        return produtoRepository.findByCategoriaIgnoreCase(categoria);
    }
}