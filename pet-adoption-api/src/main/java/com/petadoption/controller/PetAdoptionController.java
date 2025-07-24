package com.petadoption.controller;

import com.petadoption.dto.PetAdoptionDto;
import com.petadoption.exception.PetAdoptionNotFoundException;
import com.petadoption.service.PetAdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pet-adoptions")
public class PetAdoptionController {

    @Autowired
    private PetAdoptionService petAdoptionService;

    @GetMapping
    public ResponseEntity<List<PetAdoptionDto>> getPetAdoptions() {
        List<PetAdoptionDto> petAdoptions = petAdoptionService.getPetAdoptions();
        return ResponseEntity.ok(petAdoptions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetAdoptionDto> getPetAdoption(@PathVariable String id) throws PetAdoptionNotFoundException {
        Optional<PetAdoptionDto> petAdoption = petAdoptionService.getPetAdoption(id);
        return ResponseEntity.ok(petAdoption.orElse(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetAdoptionDto> updatePetAdoption(@PathVariable String id, @RequestBody PetAdoptionDto updatedPetAdoptionDto) throws PetAdoptionNotFoundException {
        Optional<PetAdoptionDto> updatedPetAdoption = petAdoptionService.updatePetAdoption(id, updatedPetAdoptionDto);
        return ResponseEntity.ok(updatedPetAdoption.orElse(null));
    }

    @PutMapping("/{id}/update-status")
    public ResponseEntity<PetAdoptionDto> updatePetAdoptionStatus(@PathVariable String id
            , @RequestBody PetAdoptionDto updatedPetAdoptionDto) throws PetAdoptionNotFoundException {
        Optional<PetAdoptionDto> updatedPetAdoption = petAdoptionService.updatePetAdoptionStatus(id
                , updatedPetAdoptionDto);
        return ResponseEntity.ok(updatedPetAdoption.orElse(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetAdoption(@PathVariable String id) throws PetAdoptionNotFoundException {
        petAdoptionService.deletePetAdoption(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PetAdoptionDto> addPetAdoption(@RequestBody PetAdoptionDto petAdoptionDto) {
        PetAdoptionDto newPetAdoption = petAdoptionService.addPetAdoption(petAdoptionDto);
        return ResponseEntity.ok(newPetAdoption);
    }

    @PostMapping("/{id}/pay-fee")
    public ResponseEntity<PetAdoptionDto> payForPetAdoption(@RequestBody PetAdoptionDto petAdoptionDto) {
        PetAdoptionDto newPetAdoption = petAdoptionService.payForPetAdoption(petAdoptionDto);
        return ResponseEntity.ok(newPetAdoption);
    }
}
