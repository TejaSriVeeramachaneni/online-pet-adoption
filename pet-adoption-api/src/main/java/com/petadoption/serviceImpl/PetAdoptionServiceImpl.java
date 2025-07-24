package com.petadoption.serviceImpl;

import com.petadoption.dto.PetAdoptionDto;
import com.petadoption.dto.PetDto;
import com.petadoption.entity.*;
import com.petadoption.exception.PetAdoptionNotFoundException;
import com.petadoption.repository.PaymentRepository;
import com.petadoption.repository.PetAdoptionRepository;
import com.petadoption.repository.PetRepository;
import com.petadoption.repository.UserRepository;
import com.petadoption.service.PetAdoptionService;
import com.petadoption.service.PetService;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetAdoptionServiceImpl implements PetAdoptionService {

    @Autowired
    private PetAdoptionRepository petAdoptionRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetService petService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PetAdoptionDto> getPetAdoptions() {
        List<PetAdoptionEntity> petAdoptionEntities = petAdoptionRepository.findAll();

        return petAdoptionEntities.stream().map(petAdoptionEntity -> {
            PetAdoptionDto petAdoptionDto = modelMapper.map(petAdoptionEntity, PetAdoptionDto.class);
            Optional<UserEntity> userEntity = userRepository.findById(petAdoptionEntity.getUserId());
            userEntity.ifPresent(entity -> petAdoptionDto.setEmail(entity.getEmail()));

            PaymentsEntity paymentsEntity = paymentRepository.findByUserId(petAdoptionDto.getUserId());

            if (paymentsEntity != null) {
                List<PaymentDetailsEntity> payments = paymentsEntity.getPayments();
                Optional<PaymentDetailsEntity> matchingPayment = payments.stream()
                        .filter(paymentDetailsEntity ->
                                paymentDetailsEntity.getAdoptionId().equals(petAdoptionEntity.getAdoptionId()))
                        .findFirst();

                if (matchingPayment.isPresent()) {
                    PaymentDetailsEntity matchingPaymentDetails = matchingPayment.get();
                    petAdoptionDto.setPaymentStatus(matchingPaymentDetails.getStatus());
                    petAdoptionDto.setTotalPrice(matchingPaymentDetails.getTotalAmount());
                }
            }

            Optional<PetDto> petDto = petService.getPet(petAdoptionDto.getPetId());
            petDto.ifPresent(petAdoptionDto::setPetDto);
            return petAdoptionDto;
        }).toList();
    }

    @Override
    public Optional<PetAdoptionDto> getPetAdoption(String id) throws PetAdoptionNotFoundException {
        Optional<PetAdoptionEntity> petAdoptionEntity = petAdoptionRepository.findById(id);
        if (petAdoptionEntity.isEmpty()) {
            throw new PetAdoptionNotFoundException("Pet Adoption not found");
        } else {
            return Optional.of(modelMapper.map(petAdoptionEntity.get(), PetAdoptionDto.class));
        }
    }

    @Override
    public PetAdoptionDto addPetAdoption(PetAdoptionDto petAdoptionDto) {
        String id = new ObjectId().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentDate = LocalDateTime.now().format(formatter);
        PetAdoptionEntity petAdoptionEntity = modelMapper.map(petAdoptionDto, PetAdoptionEntity.class);
        petAdoptionEntity.setAdoptionId(id);
        petAdoptionEntity.setApplicationDate(currentDate);

        Optional<PetEntity> petEntity = petRepository.findById(petAdoptionDto.getPetId());

        if (petEntity.isPresent()) {
            PetEntity updatedPetEntity = petEntity.get();
            updatedPetEntity.setAdoptionStatus("Not Available");
            updatedPetEntity.setAvailableForAdoption(false);
            petRepository.save(updatedPetEntity);
        }

        PetAdoptionEntity savedPetAdoptionEntity = petAdoptionRepository.save(petAdoptionEntity);
        return modelMapper.map(savedPetAdoptionEntity, PetAdoptionDto.class);
    }

    @Override
    public PetAdoptionDto payForPetAdoption(PetAdoptionDto petAdoptionDto) {
        PaymentDetailsEntity paymentDetailsEntity = modelMapper.map(petAdoptionDto.getPaymentDetails()
                , PaymentDetailsEntity.class);
        paymentDetailsEntity.setAdoptionId(petAdoptionDto.getAdoptionId());
        paymentDetailsEntity.setPaymentId(new ObjectId().toString());
        paymentDetailsEntity.setPaymentDate(LocalDateTime.now());
        paymentDetailsEntity.setStatus("Paid");
        paymentDetailsEntity.setTotalAmount(petAdoptionDto.getTotalPrice());

        PaymentsEntity paymentsEntity = paymentRepository.findByUserId(petAdoptionDto.getUserId());

        if (paymentsEntity == null) {
            paymentsEntity = new PaymentsEntity();
            paymentsEntity.setUserId(petAdoptionDto.getUserId());
            paymentsEntity.setUsername(petAdoptionDto.getUsername());
            List<PaymentDetailsEntity> payments = new ArrayList<>();
            payments.add(paymentDetailsEntity);
            paymentsEntity.setPayments(payments);
        } else {
            List<PaymentDetailsEntity> payments = paymentsEntity.getPayments();
            payments.add(paymentDetailsEntity);
            paymentsEntity.setPayments(payments);
        }
        paymentRepository.save(paymentsEntity);

        return modelMapper.map(petAdoptionDto, PetAdoptionDto.class);
    }

    @Override
    public Optional<PetAdoptionDto> updatePetAdoption(String id, PetAdoptionDto updatedPetAdoptionDto) throws PetAdoptionNotFoundException {
        Optional<PetAdoptionEntity> existingPetAdoptionEntity = petAdoptionRepository.findById(id);
        if (existingPetAdoptionEntity.isEmpty()) throw new PetAdoptionNotFoundException("Pet Adoption not found");

        PetAdoptionEntity updatedPetAdoptionEntity = modelMapper.map(updatedPetAdoptionDto, PetAdoptionEntity.class);
        updatedPetAdoptionEntity.setAdoptionId(id);
        petAdoptionRepository.save(updatedPetAdoptionEntity);

        return Optional.of(modelMapper.map(updatedPetAdoptionEntity, PetAdoptionDto.class));
    }

    @Override
    public void deletePetAdoption(String id) throws PetAdoptionNotFoundException {
        Optional<PetAdoptionEntity> existingPetAdoptionEntity = petAdoptionRepository.findById(id);
        if (existingPetAdoptionEntity.isEmpty()) throw new PetAdoptionNotFoundException("Pet Adoption not found");

        petAdoptionRepository.deleteById(id);
    }

    @Override
    public List<PetAdoptionDto> getPetAdoptionsByUser(String userId) {
        return null;
    }

    @Override
    public List<PetAdoptionDto> getPetAdoptionsByPet(String petId) {
        return null;
    }

    @Override
    public Optional<PetAdoptionDto> updatePetAdoptionStatus(String id, PetAdoptionDto updatedPetAdoptionDto) {
        Optional<PetAdoptionEntity> existingPetAdoptionEntity = petAdoptionRepository.findById(id);
        if (existingPetAdoptionEntity.isEmpty()) throw new PetAdoptionNotFoundException("Pet Adoption not found");

        PetAdoptionEntity updatedPetAdoptionEntity = existingPetAdoptionEntity.get();
        updatedPetAdoptionEntity.setStatus(updatedPetAdoptionDto.getStatus());
        if (updatedPetAdoptionDto.getStatus() != null && updatedPetAdoptionDto.getStatus().equals("Rejected")) {
            Optional<PetEntity> petEntity = petRepository.findById(updatedPetAdoptionEntity.getPetId());

            PaymentsEntity paymentsEntity = paymentRepository.findByUserId(updatedPetAdoptionEntity.getUserId());

            if (paymentsEntity != null) {
                List<PaymentDetailsEntity> payments = paymentsEntity.getPayments();
                payments.forEach(paymentDetailsEntity -> {
                    if (paymentDetailsEntity.getAdoptionId().equals(updatedPetAdoptionEntity.getAdoptionId())) {
                        paymentDetailsEntity.setStatus("Refunded");
                    }
                });
                paymentsEntity.setPayments(payments);
                paymentRepository.save(paymentsEntity);
            }

            if (petEntity.isPresent()) {
                PetEntity updatedPetEntity = petEntity.get();
                updatedPetEntity.setAdoptionStatus("Available");
                updatedPetEntity.setAvailableForAdoption(true);
                petRepository.save(updatedPetEntity);
            }
        }

        petAdoptionRepository.save(updatedPetAdoptionEntity);

        return Optional.of(modelMapper.map(updatedPetAdoptionEntity, PetAdoptionDto.class));
    }
}
