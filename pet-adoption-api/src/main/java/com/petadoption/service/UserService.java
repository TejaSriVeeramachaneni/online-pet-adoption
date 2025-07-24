package com.petadoption.service;

import com.petadoption.dto.PetAdoptionDto;
import com.petadoption.dto.PetDonationDto;
import com.petadoption.dto.UserDto;
import com.petadoption.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getUsers();

    Optional<UserDto> getUser(String id) throws UserNotFoundException;

    UserDto addUser(UserDto userDto);

    Optional<UserDto> updateUser(String id, UserDto updatedUserDto) throws UserNotFoundException;

    void deleteUser(String id) throws UserNotFoundException;

    UserDto findByEmail(String email) throws UserNotFoundException;

    UserDto findByUsername(String username) throws UserNotFoundException;

    UserDto login(UserDto userDto);

    List<PetAdoptionDto> getUserPetAdoptions(String id);

    List<PetDonationDto> getUserPetDonations(String id);
}
