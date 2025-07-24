package com.petadoption.repository;

import com.petadoption.entity.BreedEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreedRepository extends MongoRepository<BreedEntity, String> {
    // Add custom queries if needed
}
