package com.petadoption.service;

import com.petadoption.dto.PaymentDto;
import com.petadoption.exception.PaymentNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    List<PaymentDto> getPayments();

    Optional<PaymentDto> getPayment(String id) throws PaymentNotFoundException;

    PaymentDto addPayment(PaymentDto paymentDto);

    Optional<PaymentDto> updatePayment(String id, PaymentDto updatedPaymentDto) throws PaymentNotFoundException;

    void deletePayment(String id) throws PaymentNotFoundException;

    List<PaymentDto> getPaymentsByUser(String userName);

    List<PaymentDto> getPaymentsByAdoption(String adoptionId);
}
