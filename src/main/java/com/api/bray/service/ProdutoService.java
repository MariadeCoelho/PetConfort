package com.api.bray.service;

import com.api.bray.model.Produto;
import com.api.bray.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    // Listar todos os produtos
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();  // Esse é o método único para listar todos os produtos
    }

    public Produto buscarProdutoPorId(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.orElse(null);
    }

    public void atualizarProduto(Produto produto) {
        produtoRepository.save(produto);  // Salva ou atualiza o produto
    }

    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);  // Deleta o produto pelo ID
    }
}
