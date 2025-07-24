package com.petadoption.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetAdoptionDto {
    private String adoptionId;
    private String userId;
    private String username;
    private String email;
    private String petId;
    private String applicationDate;
    private String status;
    private String paymentStatus;
    private double totalPrice;
    private PetDto petDto;
    private PaymentDetailsDto paymentDetails;

}
