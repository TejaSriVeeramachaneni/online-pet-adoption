package com.petadoption.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "pet_adoptions")
public class PetAdoptionEntity {

    @Id
    private String adoptionId;
    private String userId;
    private String username;
    private String petId;
    private String applicationDate;
    private String status;
    private double totalPrice;

}
