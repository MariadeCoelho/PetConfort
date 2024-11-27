package com.api.bray.controller;

import com.api.bray.model.Produto;
import com.api.bray.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Método para exibir a lista de produtos
    @GetMapping("/listar")
    public String listarProdutos(Model model) {
        List<Produto> produtos = produtoService.listarProdutos();
        model.addAttribute("produtos", produtos);
        return "listaProduto"; // Nome do arquivo HTML
    }

    // Método para exibir o formulário de cadastro de produto
    @GetMapping("/cadastroProduto")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("produto", new Produto());
        return "cadastroProduto"; // Nome do arquivo HTML
    }

    // Método para salvar o produto após o cadastro
    @PostMapping("/salvar")
    public String salvarProduto(@ModelAttribute Produto produto) {
        produtoService.salvarProduto(produto);
        return "redirect:/produto/listar"; // Redireciona para a lista de produtos
    }

    // Método para exibir o formulário de edição de produto
    @GetMapping("/editarProduto/{id}")
    public String exibirFormularioEdicao(@PathVariable("id") Long id, Model model) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        if (produto != null) {
            model.addAttribute("produto", produto);
            return "editarProduto"; // Nome do arquivo HTML
        }
        return "redirect:/produto/listar"; // Redireciona se não encontrar o produto
    }

    // Método para atualizar os dados do produto
    @PostMapping("/atualizarProduto")
    public String atualizarProduto(@ModelAttribute Produto produto) {
        produtoService.atualizarProduto(produto);
        return "redirect:/produto/listar"; // Redireciona para a lista de produtos
    }

    // Método para excluir um produto
    @GetMapping("/deletarProduto/{id}")
    public String deletarProduto(@PathVariable("id") Long id) {
        produtoService.deletarProduto(id);
        return "redirect:/produto/listar"; // Redireciona para a lista de produtos
    }
}
