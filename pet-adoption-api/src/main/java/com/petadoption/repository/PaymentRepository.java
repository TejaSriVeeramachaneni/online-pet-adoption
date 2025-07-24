package com.petadoption.repository;

import com.petadoption.entity.PaymentsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends MongoRepository<PaymentsEntity, String> {


    PaymentsEntity findByUserId(String userId);
}
