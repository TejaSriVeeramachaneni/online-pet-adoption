package com.petadoption.controller;

import com.petadoption.config.UserAuthenticationProvider;
import com.petadoption.dto.PetAdoptionDto;
import com.petadoption.dto.PetDonationDto;
import com.petadoption.dto.UserDto;
import com.petadoption.exception.UserNotFoundException;
import com.petadoption.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) throws UserNotFoundException {
        Optional<UserDto> user = userService.getUser(id);
        return ResponseEntity.ok(user.orElse(null));
    }

    @GetMapping("/{id}/pet-adoptions")
    public ResponseEntity<List<PetAdoptionDto>> getUserPetAdoptions(@PathVariable String id) throws UserNotFoundException {
        List<PetAdoptionDto> pets = userService.getUserPetAdoptions(id);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}/pet-donations")
    public ResponseEntity<List<PetDonationDto>> getUserPetDonations(@PathVariable String id)
            throws UserNotFoundException {
        List<PetDonationDto> petDonations = userService.getUserPetDonations(id);
        return ResponseEntity.ok(petDonations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id
            , @RequestBody UserDto updatedUserDto) throws UserNotFoundException {
        Optional<UserDto> updatedUser = userService.updateUser(id, updatedUserDto);
        return ResponseEntity.ok(updatedUser.orElse(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        UserDto newUser = userService.addUser(userDto);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto) {
        UserDto user = userService.login(userDto);
        user.setToken(userAuthenticationProvider.createToken(userDto.getEmail(), user.getUserId(), "ROLE_USER"));
        return ResponseEntity.ok(user);
    }
}
