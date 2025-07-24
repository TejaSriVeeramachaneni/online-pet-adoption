package com.petadoption.serviceImpl;

import com.petadoption.dto.PetAdoptionDto;
import com.petadoption.dto.PetDonationDto;
import com.petadoption.dto.PetDto;
import com.petadoption.dto.UserDto;
import com.petadoption.entity.*;
import com.petadoption.exception.UserNotFoundException;
import com.petadoption.repository.PaymentRepository;
import com.petadoption.repository.PetAdoptionRepository;
import com.petadoption.repository.PetDonationRepository;
import com.petadoption.repository.UserRepository;
import com.petadoption.service.PetService;
import com.petadoption.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetAdoptionRepository petAdoptionRepository;
    @Autowired
    private PetDonationRepository petDonationRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PetService petService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDto> getUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(userEntity -> modelMapper.map(userEntity, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> getUser(String id) throws UserNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("User not found");
        } else {
            UserEntity updatedUser = userEntity.get();
            updatedUser.setPassword(null);
            updatedUser.setRole("ROLE_USER");
            return Optional.of(modelMapper.map(updatedUser, UserDto.class));
        }
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return modelMapper.map(savedUserEntity, UserDto.class);
    }

    @Override
    public Optional<UserDto> updateUser(String id, UserDto updatedUserDto) throws UserNotFoundException {
        Optional<UserEntity> existingUserEntity = userRepository.findById(id);
        if (existingUserEntity.isEmpty()) throw new UserNotFoundException("User not found");

        UserEntity updatedUserEntity = modelMapper.map(updatedUserDto, UserEntity.class);
        updatedUserEntity.setUserId(id);
        userRepository.save(updatedUserEntity);

        return Optional.of(modelMapper.map(updatedUserEntity, UserDto.class));
    }

    @Override
    public void deleteUser(String id) throws UserNotFoundException {
        Optional<UserEntity> existingUserEntity = userRepository.findById(id);
        if (existingUserEntity.isEmpty()) throw new UserNotFoundException("User not found");

        userRepository.deleteById(id);
    }

    @Override
    public UserDto findByEmail(String email) throws UserNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Unknown user"));

        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto findByUsername(String username) throws UserNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Unknown user"));

        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto login(UserDto customerDto) {
        UserEntity userEntity = userRepository.findByEmail(customerDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (userEntity.getPassword() != null && userEntity.getPassword().equals(customerDto.getPassword())) {
            UserDto finalCustomerDetails = modelMapper.map(userEntity, UserDto.class);
            finalCustomerDetails.setPassword(null);
            finalCustomerDetails.setRole("ROLE_USER");
            return finalCustomerDetails;
        }
        throw new UserNotFoundException("Invalid password");
    }

    @Override
    public List<PetAdoptionDto> getUserPetAdoptions(String id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<PetAdoptionEntity> petAdoptionEntities = petAdoptionRepository.findByUserId(id);

        return petAdoptionEntities.stream().map(petAdoptionEntity -> {
            PetAdoptionDto petAdoptionDto = modelMapper.map(petAdoptionEntity, PetAdoptionDto.class);
            Optional<PetDto> petDto = petService.getPet(petAdoptionDto.getPetId());
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
            petDto.ifPresent(petAdoptionDto::setPetDto);
            return petAdoptionDto;
        }).toList();
    }

    @Override
    public List<PetDonationDto> getUserPetDonations(String id) {
        List<PetDonationEntity> petDonations = petDonationRepository.findByUserId(id);

        return petDonations.stream().map(petDonationEntity -> {
            PetDonationDto petDonationDto = modelMapper.map(petDonationEntity, PetDonationDto.class);
            Optional<PetDto> petDto = petService.getPet(petDonationEntity.getPetId());
            petDto.ifPresent(petDonationDto::setPetDto);
            return petDonationDto;
        }).collect(Collectors.toList());
    }


}
