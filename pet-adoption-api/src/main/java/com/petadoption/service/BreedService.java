package com.petadoption.service;

import com.petadoption.dto.BreedDto;
import com.petadoption.exception.BreedNotFoundException;

import java.util.List;
import java.util.Optional;

public interface BreedService {
    List<BreedDto> getBreeds();

    Optional<BreedDto> getBreed(String id) throws BreedNotFoundException;

    BreedDto addBreed(BreedDto breedDto);

    Optional<BreedDto> updateBreed(String id, BreedDto updatedBreedDto) throws BreedNotFoundException;

    void deleteBreed(String id) throws BreedNotFoundException;
}
