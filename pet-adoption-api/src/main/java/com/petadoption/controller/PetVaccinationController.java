package com.petadoption.controller;

import com.petadoption.dto.PetVaccinationDto;
import com.petadoption.exception.PetVaccinationNotFoundException;
import com.petadoption.service.PetVaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pet-vaccinations")
public class PetVaccinationController {

    @Autowired
    private PetVaccinationService petVaccinationService;

    @GetMapping
    public ResponseEntity<List<PetVaccinationDto>> getPetVaccinations(@RequestParam String petId) {
        List<PetVaccinationDto> petVaccinations = petVaccinationService.getPetVaccinations(petId);
        return ResponseEntity.ok(petVaccinations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetVaccinationDto> getPetVaccination(@PathVariable String id) throws PetVaccinationNotFoundException {
        Optional<PetVaccinationDto> petVaccination = petVaccinationService.getPetVaccination(id);
        return ResponseEntity.ok(petVaccination.orElse(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetVaccinationDto> updatePetVaccination(@PathVariable String id, @RequestBody PetVaccinationDto updatedPetVaccinationDto) throws PetVaccinationNotFoundException {
        Optional<PetVaccinationDto> updatedPetVaccination = petVaccinationService.updatePetVaccination(id, updatedPetVaccinationDto);
        return ResponseEntity.ok(updatedPetVaccination.orElse(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetVaccination(@PathVariable String id) throws PetVaccinationNotFoundException {
        petVaccinationService.deletePetVaccination(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PetVaccinationDto> addPetVaccination(@RequestBody PetVaccinationDto petVaccinationDto) {
        PetVaccinationDto newPetVaccination = petVaccinationService.addPetVaccination(petVaccinationDto);
        return ResponseEntity.ok(newPetVaccination);
    }

    @PostMapping("/pet/{petId}")
    public ResponseEntity<PetVaccinationDto> addPetVaccinationDetails(@RequestBody PetVaccinationDto petVaccinationDto
            , @PathVariable String petId) {
        PetVaccinationDto newPetVaccination = petVaccinationService.addPetVaccinationDetails(petVaccinationDto, petId);
        return ResponseEntity.ok(newPetVaccination);
    }

    @PutMapping("/{id}/pet/{petId}")
    public ResponseEntity<PetVaccinationDto> updatePetVaccinationDetails(@RequestBody PetVaccinationDto petVaccinationDto
            , @PathVariable String petId) {
        PetVaccinationDto updatedPetVaccination = petVaccinationService.updatePetVaccinationDetails(petVaccinationDto, petId);
        return ResponseEntity.ok(updatedPetVaccination);
    }
}
