package com.petadoption.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "pet_vaccinations")
public class PetVaccinationEntity {

    @Id
    private String vaccinationId;

    private String petId;
    private String vaccineName;
    private String vaccinationDate;
    private String nextVaccinationDate;

    // You can include other relevant fields or methods as needed
}
