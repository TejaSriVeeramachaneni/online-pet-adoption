package com.petadoption.service;

import com.petadoption.dto.PetDto;
import com.petadoption.exception.PetNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PetService {
    List<PetDto> getPets();

    Optional<PetDto> getPet(String id) throws PetNotFoundException;

    PetDto addPet(PetDto petDto);

    Optional<PetDto> updatePet(String id, PetDto updatedPetDto) throws PetNotFoundException;

    void deletePet(String id) throws PetNotFoundException;

    List<PetDto> getPetsByCategory(String categoryId);

    List<PetDto> getPetsByAdoptionStatus(String adoptionStatus);
}
