package com.petadoption.repository;

import com.petadoption.entity.PetVaccinationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetVaccinationRepository extends MongoRepository<PetVaccinationEntity, String> {
    List<PetVaccinationEntity> findByPetId(String petId);
    // Add custom queries if needed
}
