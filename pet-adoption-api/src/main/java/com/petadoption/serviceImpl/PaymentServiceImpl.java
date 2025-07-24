package com.petadoption.serviceImpl;

import com.petadoption.dto.PaymentDto;
import com.petadoption.entity.PaymentsEntity;
import com.petadoption.exception.PaymentNotFoundException;
import com.petadoption.repository.PaymentRepository;
import com.petadoption.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PaymentDto> getPayments() {
        List<PaymentsEntity> payments = paymentRepository.findAll();
        return payments.stream().map(paymentEntity -> modelMapper.map(paymentEntity, PaymentDto.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<PaymentDto> getPayment(String id) throws PaymentNotFoundException {
        Optional<PaymentsEntity> paymentEntity = paymentRepository.findById(id);
        if (paymentEntity.isEmpty()) {
            throw new PaymentNotFoundException("Payment not found");
        } else {
            return Optional.of(modelMapper.map(paymentEntity.get(), PaymentDto.class));
        }
    }

    @Override
    public PaymentDto addPayment(PaymentDto paymentDto) {
        PaymentsEntity paymentEntity = modelMapper.map(paymentDto, PaymentsEntity.class);
        PaymentsEntity savedPaymentsEntity = paymentRepository.save(paymentEntity);
        return modelMapper.map(savedPaymentsEntity, PaymentDto.class);
    }

    @Override
    public Optional<PaymentDto> updatePayment(String id, PaymentDto updatedPaymentDto) throws PaymentNotFoundException {
        Optional<PaymentsEntity> existingPaymentsEntity = paymentRepository.findById(id);
        if (existingPaymentsEntity.isEmpty()) throw new PaymentNotFoundException("Payment not found");

        PaymentsEntity updatedPaymentsEntity = modelMapper.map(updatedPaymentDto, PaymentsEntity.class);
        updatedPaymentsEntity.setPaymentId(id);
        paymentRepository.save(updatedPaymentsEntity);

        return Optional.of(modelMapper.map(updatedPaymentsEntity, PaymentDto.class));
    }

    @Override
    public void deletePayment(String id) throws PaymentNotFoundException {
        Optional<PaymentsEntity> existingPaymentsEntity = paymentRepository.findById(id);
        if (existingPaymentsEntity.isEmpty()) throw new PaymentNotFoundException("Payment not found");

        paymentRepository.deleteById(id);
    }

    @Override
    public List<PaymentDto> getPaymentsByUser(String userName) {
        return null;
    }

    @Override
    public List<PaymentDto> getPaymentsByAdoption(String adoptionId) {
        return null;
    }
}
