package com.petadoption.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private String paymentId;
    private String userName;
    private List<String> paymentDetails;
    private String adoptionId;
    private String paymentType;
    private String cardNumber;
    private String cardName;
    private int expiryMonth;
    private int expiryYear;
    private String cvv;
    private String status;
    private double totalAmount;
    private String paymentDate;
}
