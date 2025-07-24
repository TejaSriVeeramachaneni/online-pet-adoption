package com.petadoption.repository;

import com.petadoption.entity.PetAdoptionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetAdoptionRepository extends MongoRepository<PetAdoptionEntity, String> {
    List<PetAdoptionEntity> findByUserId(String userId);

    List<PetAdoptionEntity> findByPetId(String petId);
    // Add custom queries if needed
}
