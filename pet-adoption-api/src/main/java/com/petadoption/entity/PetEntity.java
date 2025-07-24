package com.petadoption.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "pets")
public class PetEntity {

    @Id
    private String petId;

    private String name;
    private String breedId;
    private String categoryId;
    private int age;
    private String gender;
    private String size;
    private String color;
    private String status;
    private String imageUrl;
    private String temperament;
    private String specialRequirements;
    private boolean availableForAdoption;
    private double adoptionFee;
    private String donorId;
    private String adoptionStatus;

    // You can include other relevant fields or methods as needed
}
