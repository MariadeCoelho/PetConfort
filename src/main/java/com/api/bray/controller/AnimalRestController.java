package com.api.bray.controller;

import com.api.bray.model.Animal;
import com.api.bray.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animais")
public class AnimalRestController {

    @Autowired
    private AnimalService animalService;

    // Endpoint para listar os animais
    @GetMapping
    public ResponseEntity<?> listarAnimais() {
        return ResponseEntity.ok(animalService.listarAnimais());
    }

    // Endpoint para buscar um animal por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarAnimalPorId(@PathVariable Long id) {
        Animal animal = animalService.buscarAnimalPorId(id);
        if (animal != null) {
            return ResponseEntity.ok(animal);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Animal não encontrado");
    }

    // Endpoint para editar (atualizar) um animal
    @PutMapping("/{id}")
    public ResponseEntity<?> editarAnimal(@PathVariable Long id, @RequestBody Animal animal) {
        Animal animalExistente = animalService.buscarAnimalPorId(id);
        if (animalExistente != null) {
            animal.setId(id); // Garante que o ID do animal seja mantido
            animalService.atualizarAnimal(animal);
            return ResponseEntity.ok(animal);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Animal não encontrado para atualização");
    }

    // Endpoint para deletar um animal
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarAnimal(@PathVariable Long id) {
        Animal animal = animalService.buscarAnimalPorId(id);
        if (animal != null) {
            animalService.deletarAnimal(id);
            return ResponseEntity.ok("Animal deletado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Animal não encontrado para deleção");
    }

    // Endpoint para salvar um novo animal (POST)
    @PostMapping
    public ResponseEntity<?> salvarAnimal(@RequestBody Animal animal) {
        Animal animalSalvo = animalService.salvarAnimal(animal); // Chama o método da service para salvar o animal
        return ResponseEntity.status(HttpStatus.CREATED).body(animalSalvo); // Retorna o animal salvo com o status 201
    }
}
