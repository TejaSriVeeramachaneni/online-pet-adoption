package com.petadoption.controller;

import com.petadoption.dto.PaymentDto;
import com.petadoption.exception.PaymentNotFoundException;
import com.petadoption.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentDto>> getPayments() {
        List<PaymentDto> payments = paymentService.getPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable String id) throws PaymentNotFoundException {
        Optional<PaymentDto> payment = paymentService.getPayment(id);
        return ResponseEntity.ok(payment.orElse(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable String id, @RequestBody PaymentDto updatedPaymentDto) throws PaymentNotFoundException {
        Optional<PaymentDto> updatedPayment = paymentService.updatePayment(id, updatedPaymentDto);
        return ResponseEntity.ok(updatedPayment.orElse(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable String id) throws PaymentNotFoundException {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PaymentDto> addPayment(@RequestBody PaymentDto paymentDto) {
        PaymentDto newPayment = paymentService.addPayment(paymentDto);
        return ResponseEntity.ok(newPayment);
    }
}
