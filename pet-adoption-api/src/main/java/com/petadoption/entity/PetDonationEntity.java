package com.petadoption.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "pet_donations")
public class PetDonationEntity {

    @Id
    private String donationId;

    private String userId;
    private String username;
    private String petId;
    private String submissionDate;
    private String status;

}
