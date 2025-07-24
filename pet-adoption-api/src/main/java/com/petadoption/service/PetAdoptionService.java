package com.petadoption.service;

import com.petadoption.dto.PetAdoptionDto;
import com.petadoption.exception.PetAdoptionNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PetAdoptionService {
    List<PetAdoptionDto> getPetAdoptions();

    Optional<PetAdoptionDto> getPetAdoption(String id) throws PetAdoptionNotFoundException;

    PetAdoptionDto addPetAdoption(PetAdoptionDto petAdoptionDto);

    Optional<PetAdoptionDto> updatePetAdoption(String id, PetAdoptionDto updatedPetAdoptionDto) throws PetAdoptionNotFoundException;

    void deletePetAdoption(String id) throws PetAdoptionNotFoundException;

    List<PetAdoptionDto> getPetAdoptionsByUser(String userId);

    List<PetAdoptionDto> getPetAdoptionsByPet(String petId);

    Optional<PetAdoptionDto> updatePetAdoptionStatus(String id, PetAdoptionDto updatedPetAdoptionDto);

    PetAdoptionDto payForPetAdoption(PetAdoptionDto petAdoptionDto);
}
