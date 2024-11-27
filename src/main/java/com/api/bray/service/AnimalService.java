package com.api.bray.service;

import com.api.bray.model.Animal;
import com.api.bray.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    // Salvar animal
    public Animal salvarAnimal(Animal animal) {
        return animalRepository.save(animal); // Salva o animal no banco de dados
    }

    // Listar todos os animais
    public List<Animal> listarAnimais() {
        return animalRepository.findAll();
    }

    // Buscar animal por ID
    public Animal buscarAnimalPorId(Long id) {
        Optional<Animal> animal = animalRepository.findById(id);
        return animal.orElse(null); // Retorna null se não encontrar o animal
    }

    // Atualizar animal
    public void atualizarAnimal(Animal animal) {
        animalRepository.save(animal); // Salva ou atualiza o animal
    }

    // Deletar animal
    public void deletarAnimal(Long id) {
        animalRepository.deleteById(id); // Deleta o animal pelo ID
    }
}
