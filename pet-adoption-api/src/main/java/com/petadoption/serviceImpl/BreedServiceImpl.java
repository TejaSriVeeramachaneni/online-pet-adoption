package com.petadoption.serviceImpl;

import com.petadoption.dto.BreedDto;
import com.petadoption.entity.BreedEntity;
import com.petadoption.exception.BreedNotFoundException;
import com.petadoption.repository.BreedRepository;
import com.petadoption.service.BreedService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BreedServiceImpl implements BreedService {

    @Autowired
    private BreedRepository breedRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<BreedDto> getBreeds() {
        List<BreedEntity> breeds = breedRepository.findAll();
        return breeds.stream().map(breedEntity -> modelMapper.map(breedEntity, BreedDto.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<BreedDto> getBreed(String id) throws BreedNotFoundException {
        Optional<BreedEntity> breedEntity = breedRepository.findById(id);
        if (breedEntity.isEmpty()) {
            throw new BreedNotFoundException("Breed not found");
        } else {
            return Optional.of(modelMapper.map(breedEntity.get(), BreedDto.class));
        }
    }

    @Override
    public BreedDto addBreed(BreedDto breedDto) {
        BreedEntity breedEntity = modelMapper.map(breedDto, BreedEntity.class);
        BreedEntity savedBreedEntity = breedRepository.save(breedEntity);
        return modelMapper.map(savedBreedEntity, BreedDto.class);
    }

    @Override
    public Optional<BreedDto> updateBreed(String id, BreedDto updatedBreedDto) throws BreedNotFoundException {
        Optional<BreedEntity> existingBreedEntity = breedRepository.findById(id);
        if (existingBreedEntity.isEmpty()) throw new BreedNotFoundException("Breed not found");

        BreedEntity updatedBreedEntity = modelMapper.map(updatedBreedDto, BreedEntity.class);
        updatedBreedEntity.setBreedId(id);
        breedRepository.save(updatedBreedEntity);

        return Optional.of(modelMapper.map(updatedBreedEntity, BreedDto.class));
    }

    @Override
    public void deleteBreed(String id) throws BreedNotFoundException {
        Optional<BreedEntity> existingBreedEntity = breedRepository.findById(id);
        if (existingBreedEntity.isEmpty()) throw new BreedNotFoundException("Breed not found");

        breedRepository.deleteById(id);
    }
}
