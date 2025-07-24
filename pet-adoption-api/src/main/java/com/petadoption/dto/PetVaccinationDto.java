package com.petadoption.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetVaccinationDto {
    private String vaccinationId;
    private String petId;
    private String vaccineName;
    private String vaccinationDate;
    private String nextVaccinationDate;
}
