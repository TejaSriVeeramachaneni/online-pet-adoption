package com.petadoption.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDonationDto {
    private String donationId;
    private String userId;
    private String username;
    private String email;
    private String petId;
    private String submissionDate;
    private String status;
    private PetDto petDto;
}
