package com.petadoption.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petadoption.dto.PetDonationDto;
import com.petadoption.dto.PetDto;
import com.petadoption.exception.PetDonationNotFoundException;
import com.petadoption.service.PetDonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pet-donations")
public class PetDonationController {

    @Autowired
    private PetDonationService petDonationService;

    @GetMapping
    public ResponseEntity<List<PetDonationDto>> getPetDonations() {
        List<PetDonationDto> petDonations = petDonationService.getPetDonations();
        return ResponseEntity.ok(petDonations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDonationDto> getPetDonation(@PathVariable String id) throws PetDonationNotFoundException {
        Optional<PetDonationDto> petDonation = petDonationService.getPetDonation(id);
        return ResponseEntity.ok(petDonation.orElse(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDonationDto> updatePetDonation(@PathVariable String id, @RequestBody PetDonationDto updatedPetDonationDto) throws PetDonationNotFoundException {
        Optional<PetDonationDto> updatedPetDonation = petDonationService.updatePetDonation(id, updatedPetDonationDto);
        return ResponseEntity.ok(updatedPetDonation.orElse(null));
    }

    @PutMapping("/{id}/update-status")
    public ResponseEntity<PetDonationDto> updatePetDonationStatus(@PathVariable String id
            , @RequestBody PetDonationDto updatedPetDonationDto) throws PetDonationNotFoundException {
        Optional<PetDonationDto> updatedPetDonation = petDonationService.updatePetDonationStatus(id
                , updatedPetDonationDto);
        return ResponseEntity.ok(updatedPetDonation.orElse(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetDonation(@PathVariable String id) throws PetDonationNotFoundException {
        petDonationService.deletePetDonation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PetDonationDto> addPetDonation(@RequestPart("image") MultipartFile image
            , @RequestPart("petDonationDto") String petDtoJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        PetDonationDto petDonationDto = objectMapper.readValue(petDtoJson, PetDonationDto.class);
        PetDto petDto = petDonationDto.getPetDto();
        petDto.setImage(image);
        petDonationDto.setPetDto(petDto);

        PetDonationDto newPetDonation = petDonationService.addPetDonation(petDonationDto);
        return ResponseEntity.ok(newPetDonation);
    }

}
