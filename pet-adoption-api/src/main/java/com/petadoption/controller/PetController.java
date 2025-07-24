package com.petadoption.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.dto.PetDto;
import com.petadoption.exception.PetNotFoundException;
import com.petadoption.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<List<PetDto>> getPets() {
        List<PetDto> pets = petService.getPets();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getPet(@PathVariable String id) throws PetNotFoundException {
        Optional<PetDto> pet = petService.getPet(id);
        return ResponseEntity.ok(pet.orElse(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable String id) throws PetNotFoundException {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDto> updatePet(@PathVariable String id
            , @RequestPart(name = "image", required = false) MultipartFile image
            , @RequestPart("petData") String petDtoJson) throws JsonProcessingException, PetNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();
        PetDto updatedPetDto = objectMapper.readValue(petDtoJson, PetDto.class);

        updatedPetDto.setImage(image);
        Optional<PetDto> updatedPet = petService.updatePet(id, updatedPetDto);
        return ResponseEntity.ok(updatedPet.orElse(null));
    }

    @PostMapping
    public ResponseEntity<PetDto> addPet(@RequestPart("image") MultipartFile image
            , @RequestPart("petData") String petDtoJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        PetDto petDto = objectMapper.readValue(petDtoJson, PetDto.class);
        petDto.setImage(image);
        PetDto newMovie = petService.addPet(petDto);
        return ResponseEntity.ok(newMovie);
    }
}
