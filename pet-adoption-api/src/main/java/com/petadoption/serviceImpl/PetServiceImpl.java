package com.petadoption.serviceImpl;

import com.petadoption.dto.PetDto;
import com.petadoption.entity.PetEntity;
import com.petadoption.exception.PetNotFoundException;
import com.petadoption.repository.PetRepository;
import com.petadoption.service.PetService;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PetDto> getPets() {
        List<PetEntity> pets = petRepository.findAll();
        return pets.stream().map(petEntity -> modelMapper.map(petEntity, PetDto.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<PetDto> getPet(String id) throws PetNotFoundException {
        Optional<PetEntity> petEntity = petRepository.findById(id);
        if (petEntity.isEmpty()) {
            throw new PetNotFoundException("Pet not found");
        } else {
            return Optional.of(modelMapper.map(petEntity.get(), PetDto.class));
        }
    }

    @Override
    public PetDto addPet(PetDto petDto) {
        String id = new ObjectId().toString();
        PetEntity petEntity = modelMapper.map(petDto, PetEntity.class);
        String fileName = handleFileUpload(id, petDto.getImage());
        petEntity.setImageUrl(fileName);
        petEntity.setPetId(id);

        PetEntity savedPetEntity = petRepository.save(petEntity);

        return modelMapper.map(savedPetEntity, PetDto.class);
    }

    @Override
    public Optional<PetDto> updatePet(String id, PetDto updatedPetDto) throws PetNotFoundException {
        Optional<PetEntity> existingPetEntity = petRepository.findById(id);
        if (existingPetEntity.isEmpty())
            throw new PetNotFoundException("Pet not found");

        PetEntity updatedPetEntity = modelMapper.map(updatedPetDto, PetEntity.class);

        String fileName = "";
        if (updatedPetDto.getImage() != null) {
            fileName = handleFileUpload(id, updatedPetDto.getImage());
            updatedPetEntity.setImageUrl(fileName);
        }

        updatedPetEntity.setPetId(id);
        petRepository.save(updatedPetEntity);

        return Optional.of(modelMapper.map(updatedPetEntity, PetDto.class));
    }

    @Override
    public void deletePet(String id) throws PetNotFoundException {
        Optional<PetEntity> existingPetEntity = petRepository.findById(id);
        if (existingPetEntity.isEmpty())
            throw new PetNotFoundException("Pet not found");

        petRepository.deleteById(id);
    }

    @Override
    public List<PetDto> getPetsByCategory(String categoryId) {
        List<PetEntity> pets = petRepository.findByCategoryId(categoryId);
        return pets.stream().map(petEntity -> modelMapper.map(petEntity, PetDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PetDto> getPetsByAdoptionStatus(String adoptionStatus) {
        return null;
    }

    private String handleFileUpload(String petId, MultipartFile movieImage) {
        String uniqueFileName = "";
        try {
            String uploadDir = "/Documents/GitHub/pet-adoption/pet-adoption-ui/src/images/pet-images";
            String originalFileName = movieImage.getOriginalFilename();
            assert originalFileName != null;
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            uniqueFileName = petId + fileExtension;

            Path filePath = Paths.get(uploadDir, uniqueFileName);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }

            Files.write(filePath, movieImage.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uniqueFileName;
    }
}
