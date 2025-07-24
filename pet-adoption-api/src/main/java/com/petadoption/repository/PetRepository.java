package com.petadoption.repository;

import com.petadoption.entity.PetEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends MongoRepository<PetEntity, String> {
    Optional<PetEntity> findByName(String name);

    List<PetEntity> findByCategoryId(String categoryId);

    List<PetEntity> findByAdoptionStatus(String adoptionStatus);
    // Add custom queries if needed
}
