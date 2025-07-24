package com.petadoption.controller;

import com.petadoption.dto.BreedDto;
import com.petadoption.exception.BreedNotFoundException;
import com.petadoption.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/breeds")
public class BreedController {

    @Autowired
    private BreedService breedService;

    @GetMapping
    public ResponseEntity<List<BreedDto>> getBreeds() {
        List<BreedDto> breeds = breedService.getBreeds();
        return ResponseEntity.ok(breeds);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BreedDto> getBreed(@PathVariable String id) throws BreedNotFoundException {
        Optional<BreedDto> breed = breedService.getBreed(id);
        return ResponseEntity.ok(breed.orElse(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BreedDto> updateBreed(@PathVariable String id, @RequestBody BreedDto updatedBreedDto) throws BreedNotFoundException {
        Optional<BreedDto> updatedBreed = breedService.updateBreed(id, updatedBreedDto);
        return ResponseEntity.ok(updatedBreed.orElse(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBreed(@PathVariable String id) throws BreedNotFoundException {
        breedService.deleteBreed(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<BreedDto> addBreed(@RequestBody BreedDto breedDto) {
        BreedDto newBreed = breedService.addBreed(breedDto);
        return ResponseEntity.ok(newBreed);
    }
}
