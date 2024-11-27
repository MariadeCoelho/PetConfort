package com.api.bray.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginasController {

    @GetMapping("/")  // A página inicial (index)
    public String index() {
        return "index";  // Renderiza o arquivo index.html
    }

    @GetMapping("/cadastroProduto")  // Mapeia a página de cadastro de produto
    public String cadastroProduto() {
        return "cadastroProduto";  // Renderiza o arquivo cadastroProduto.html
    }

    @GetMapping("/cadastroAnimal")  // Mapeia a página de cadastro de Animal
    public String cadastroAnimal() {
        return "cadastroAnimal";  // Renderiza o arquivo cadastroAnimal.html
    }

    @GetMapping("/listaProduto")  // Mapeia a página de lista de produtos
    public String listaProduto() {
        return "listaProduto";  // Renderiza o arquivo listaProduto.html
    }

    @GetMapping("/listaAnimal")  // Mapeia a página de lista de Animal
    public String listaAnimal() {
        return "listaAnimal";  // Renderiza o arquivo listaAnimal.html
    }
}
