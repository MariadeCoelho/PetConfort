package com.api.bray.controller;

import com.api.bray.model.Produto;
import com.api.bray.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoRestController {

    @Autowired
    private ProdutoService produtoService;

    // Endpoint para listar os produtos
    @GetMapping
    public ResponseEntity<?> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    // Endpoint para buscar um produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProdutoPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
    }

    // Endpoint para editar (atualizar) um produto
    @PutMapping("/{id}")
    public ResponseEntity<?> editarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        Produto produtoExistente = produtoService.buscarProdutoPorId(id);
        if (produtoExistente != null) {
            produto.setId(id); // Garante que o ID do produto seja mantido
            produtoService.atualizarProduto(produto);
            return ResponseEntity.ok(produto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado para atualização");
    }

    // Endpoint para deletar um produto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarProduto(@PathVariable Long id) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        if (produto != null) {
            produtoService.deletarProduto(id);
            return ResponseEntity.ok("Produto deletado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado para deleção");
    }

    // Endpoint para salvar um novo produto (POST)
    @PostMapping
    public ResponseEntity<?> salvarProduto(@RequestBody Produto produto) {
        Produto produtoSalvo = produtoService.salvarProduto(produto); // Chama o método da service para salvar o produto
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo); // Retorna o produto salvo com o status 201
    }
}
