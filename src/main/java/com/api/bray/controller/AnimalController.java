package com.api.bray.controller;

import com.api.bray.model.Animal;

import com.api.bray.service.AnimalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    // Rota para listar os animais
    @GetMapping("/listar")
    public String listarAnimais(Model model) {
        List<Animal> animais = animalService.listarAnimais();
        model.addAttribute("animais", animais );
        return "listaAnimal"; // Nome da página HTML (listarAnimais.html)
    }
    
    @GetMapping("/cadastroAnimal")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("animal", new Animal());
        return "cadastroAnimal"; // Nome do arquivo HTML
    }
    
      @PostMapping("/salvar")
      public String salvarAnimal(@ModelAttribute Animal animal) {
        animalService.salvarAnimal(animal);
        return "redirect:/animal/listar"; // Redireciona para a lista de produtos
    }

@GetMapping("/editar-animal/{id}")
    public String editarAnimal(@PathVariable("id") Long id, Model model) {
        Animal animal = animalService.buscarAnimalPorId(id); 
        model.addAttribute("animal", animal);
        return "editarAnimal"; // Nome do template
    }

    
    
// Rota para atualizar o animal
    @PostMapping("/atualizar")
    public String atualizarAnimal(@ModelAttribute Animal animal) {
        animalService.atualizarAnimal(animal);
        return "redirect:/animal/listar"; // Redireciona para a lista de animais
    }
    
 

    // Rota para deletar o animal
    @GetMapping("/deletarAnimal/{id}")
    public String deletarAnimal(@PathVariable Long id) {
        animalService.deletarAnimal(id);
        return "redirect:/animal/listar"; // Redireciona para a lista de animais
    }

 
}
