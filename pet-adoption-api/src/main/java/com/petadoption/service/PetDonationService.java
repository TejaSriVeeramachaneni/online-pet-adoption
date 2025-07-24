package com.petadoption.service;

import com.petadoption.dto.PetDonationDto;
import com.petadoption.exception.PetDonationNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PetDonationService {
    List<PetDonationDto> getPetDonations();

    Optional<PetDonationDto> getPetDonation(String id) throws PetDonationNotFoundException;

    PetDonationDto addPetDonation(PetDonationDto petDonationDto);

    Optional<PetDonationDto> updatePetDonation(String id, PetDonationDto updatedPetDonationDto) throws PetDonationNotFoundException;

    void deletePetDonation(String id) throws PetDonationNotFoundException;

    List<PetDonationDto> getPetDonationsByUser(String userId);

    List<PetDonationDto> getPetDonationsByPet(String petId);

    Optional<PetDonationDto> updatePetDonationStatus(String id, PetDonationDto updatedPetDonationDto);
}
