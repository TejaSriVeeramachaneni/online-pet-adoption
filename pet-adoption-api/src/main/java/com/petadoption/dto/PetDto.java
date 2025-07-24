package com.petadoption.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDto {
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
    private MultipartFile image;
    // You can include other relevant fields or methods as needed
}
