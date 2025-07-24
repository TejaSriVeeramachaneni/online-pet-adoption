package com.petadoption.repository;

import com.petadoption.entity.PetDonationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetDonationRepository extends MongoRepository<PetDonationEntity, String> {
    List<PetDonationEntity> findByUserId(String userId);

    List<PetDonationEntity> findByPetId(String petId);
    // Add custom queries if needed
}
