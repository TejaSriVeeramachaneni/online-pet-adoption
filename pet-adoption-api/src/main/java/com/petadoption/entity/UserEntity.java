package com.petadoption.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserEntity {

    @Id
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

}
