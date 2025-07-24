package com.petadoption.service;

import com.petadoption.dto.PetVaccinationDto;
import com.petadoption.exception.PetVaccinationNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PetVaccinationService {
    List<PetVaccinationDto> getPetVaccinations(String petId);

    Optional<PetVaccinationDto> getPetVaccination(String id) throws PetVaccinationNotFoundException;

    PetVaccinationDto addPetVaccination(PetVaccinationDto petVaccinationDto);

    Optional<PetVaccinationDto> updatePetVaccination(String id, PetVaccinationDto updatedPetVaccinationDto) throws PetVaccinationNotFoundException;

    void deletePetVaccination(String id) throws PetVaccinationNotFoundException;

    List<PetVaccinationDto> getPetVaccinationsByPet(String petId);

    PetVaccinationDto addPetVaccinationDetails(PetVaccinationDto petVaccinationDto, String petId);

    PetVaccinationDto updatePetVaccinationDetails(PetVaccinationDto petVaccinationDto, String petId);
}
