package com.petadoption.serviceImpl;

import com.petadoption.dto.PetVaccinationDto;
import com.petadoption.entity.PetVaccinationEntity;
import com.petadoption.exception.PetVaccinationNotFoundException;
import com.petadoption.repository.PetVaccinationRepository;
import com.petadoption.service.PetVaccinationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetVaccinationServiceImpl implements PetVaccinationService {

    @Autowired
    private PetVaccinationRepository petVaccinationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PetVaccinationDto> getPetVaccinations(String petId) {
        List<PetVaccinationEntity> petVaccinations = petId != null ? petVaccinationRepository.findByPetId(petId)
                : petVaccinationRepository.findAll();
        return petVaccinations.stream().map(petVaccinationEntity -> modelMapper.map(petVaccinationEntity, PetVaccinationDto.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<PetVaccinationDto> getPetVaccination(String id) throws PetVaccinationNotFoundException {
        Optional<PetVaccinationEntity> petVaccinationEntity = petVaccinationRepository.findById(id);
        if (petVaccinationEntity.isEmpty()) {
            throw new PetVaccinationNotFoundException("Pet Vaccination not found");
        } else {
            return Optional.of(modelMapper.map(petVaccinationEntity.get(), PetVaccinationDto.class));
        }
    }

    @Override
    public PetVaccinationDto addPetVaccination(PetVaccinationDto petVaccinationDto) {
        PetVaccinationEntity petVaccinationEntity = modelMapper.map(petVaccinationDto, PetVaccinationEntity.class);
        PetVaccinationEntity savedPetVaccinationEntity = petVaccinationRepository.save(petVaccinationEntity);
        return modelMapper.map(savedPetVaccinationEntity, PetVaccinationDto.class);
    }

    @Override
    public Optional<PetVaccinationDto> updatePetVaccination(String id, PetVaccinationDto updatedPetVaccinationDto) throws PetVaccinationNotFoundException {
        Optional<PetVaccinationEntity> existingPetVaccinationEntity = petVaccinationRepository.findById(id);
        if (existingPetVaccinationEntity.isEmpty())
            throw new PetVaccinationNotFoundException("Pet Vaccination not found");

        PetVaccinationEntity updatedPetVaccinationEntity = modelMapper.map(updatedPetVaccinationDto, PetVaccinationEntity.class);
        updatedPetVaccinationEntity.setVaccinationId(id);
        petVaccinationRepository.save(updatedPetVaccinationEntity);

        return Optional.of(modelMapper.map(updatedPetVaccinationEntity, PetVaccinationDto.class));
    }

    @Override
    public void deletePetVaccination(String id) throws PetVaccinationNotFoundException {
        Optional<PetVaccinationEntity> existingPetVaccinationEntity = petVaccinationRepository.findById(id);
        if (existingPetVaccinationEntity.isEmpty())
            throw new PetVaccinationNotFoundException("Pet Vaccination not found");

        petVaccinationRepository.deleteById(id);
    }

    @Override
    public List<PetVaccinationDto> getPetVaccinationsByPet(String petId) {
        return null;
    }

    @Override
    public PetVaccinationDto addPetVaccinationDetails(PetVaccinationDto petVaccinationDto, String petId) {
        return null;
    }

    @Override
    public PetVaccinationDto updatePetVaccinationDetails(PetVaccinationDto petVaccinationDto, String petId) {
        return null;
    }
}
