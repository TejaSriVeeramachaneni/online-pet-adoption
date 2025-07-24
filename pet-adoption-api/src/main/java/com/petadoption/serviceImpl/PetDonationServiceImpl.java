package com.petadoption.serviceImpl;

import com.petadoption.dto.PetDonationDto;
import com.petadoption.dto.PetDto;
import com.petadoption.entity.PetDonationEntity;
import com.petadoption.entity.PetEntity;
import com.petadoption.entity.UserEntity;
import com.petadoption.exception.PetDonationNotFoundException;
import com.petadoption.exception.PetNotFoundException;
import com.petadoption.repository.PetDonationRepository;
import com.petadoption.repository.PetRepository;
import com.petadoption.repository.UserRepository;
import com.petadoption.service.PetDonationService;
import com.petadoption.service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetDonationServiceImpl implements PetDonationService {

    @Autowired
    private PetDonationRepository petDonationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private PetService petService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PetDonationDto> getPetDonations() {
        List<PetDonationEntity> petDonations = petDonationRepository.findAll();
        return petDonations.stream().map(petDonationEntity -> {
            PetDonationDto petDonationDto = modelMapper.map(petDonationEntity, PetDonationDto.class);
            Optional<UserEntity> userEntity = userRepository.findById(petDonationEntity.getUserId());
            userEntity.ifPresent(entity -> {
                petDonationDto.setEmail(entity.getEmail());
                petDonationDto.setUsername(entity.getFirstName() + " " + entity.getLastName());
            });
            Optional<PetDto> petDto = petService.getPet(petDonationEntity.getPetId());
            petDto.ifPresent(petDonationDto::setPetDto);
            return petDonationDto;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<PetDonationDto> getPetDonation(String id) throws PetDonationNotFoundException {
        Optional<PetDonationEntity> petDonationEntity = petDonationRepository.findById(id);
        if (petDonationEntity.isEmpty()) {
            throw new PetDonationNotFoundException("Pet Donation not found");
        } else {
            return Optional.of(modelMapper.map(petDonationEntity.get(), PetDonationDto.class));
        }
    }

    @Override
    public PetDonationDto addPetDonation(PetDonationDto petDonationDto) {
        PetDto savedPet = petService.addPet(petDonationDto.getPetDto());

        PetDonationEntity petDonationEntity = modelMapper.map(petDonationDto, PetDonationEntity.class);
        petDonationEntity.setPetId(savedPet.getPetId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentDate = LocalDateTime.now().format(formatter);
        petDonationEntity.setSubmissionDate(currentDate);
        PetDonationEntity savedPetDonationEntity = petDonationRepository.save(petDonationEntity);
        PetDonationDto finalPetDonationDto = modelMapper.map(savedPetDonationEntity, PetDonationDto.class);
        finalPetDonationDto.setPetDto(savedPet);

        return finalPetDonationDto;
    }

    @Override
    public Optional<PetDonationDto> updatePetDonation(String id, PetDonationDto updatedPetDonationDto) throws PetDonationNotFoundException {
        Optional<PetDonationEntity> existingPetDonationEntity = petDonationRepository.findById(id);
        if (existingPetDonationEntity.isEmpty()) throw new PetDonationNotFoundException("Pet Donation not found");

        PetDonationEntity updatedPetDonationEntity = modelMapper.map(updatedPetDonationDto, PetDonationEntity.class);
        updatedPetDonationEntity.setDonationId(id);
        petDonationRepository.save(updatedPetDonationEntity);

        return Optional.of(modelMapper.map(updatedPetDonationEntity, PetDonationDto.class));
    }

    @Override
    public Optional<PetDonationDto> updatePetDonationStatus(String id
            , PetDonationDto updatedPetDonationDto) throws PetDonationNotFoundException {
        Optional<PetDonationEntity> existingPetDonationEntity = petDonationRepository.findById(id);
        if (existingPetDonationEntity.isEmpty()) throw new PetDonationNotFoundException("Pet Donation not found");

        PetDonationEntity updatedPetDonationEntity = existingPetDonationEntity.get();
        updatedPetDonationEntity.setStatus(updatedPetDonationDto.getStatus());

        if (updatedPetDonationDto.getStatus().equals("Rejected")) {
            Optional<PetEntity> existingPetEntity = petRepository.findById(existingPetDonationEntity.get().getPetId());
            if (existingPetEntity.isEmpty()) throw new PetNotFoundException("Pet not found");
            PetEntity petEntity = existingPetEntity.get();
            petEntity.setStatus(updatedPetDonationDto.getStatus());
            petRepository.save(petEntity);
        }

        petDonationRepository.save(updatedPetDonationEntity);

        return Optional.of(modelMapper.map(updatedPetDonationEntity, PetDonationDto.class));
    }

    @Override
    public void deletePetDonation(String id) throws PetDonationNotFoundException {
        Optional<PetDonationEntity> existingPetDonationEntity = petDonationRepository.findById(id);
        if (existingPetDonationEntity.isEmpty()) throw new PetDonationNotFoundException("Pet Donation not found");

        petDonationRepository.deleteById(id);
    }

    @Override
    public List<PetDonationDto> getPetDonationsByUser(String userId) {
        return null;
    }

    @Override
    public List<PetDonationDto> getPetDonationsByPet(String petId) {
        return null;
    }
}
