package com.petadoption.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String role;
    private String address;
    private String phone;
    private String ssn;
    private String token;
    private List<GrantedAuthority> authorities; // Roles and permissions
}
