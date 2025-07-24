package com.petadoption.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "breeds")
public class BreedEntity {

    @Id
    private String breedId;

    private String categoryId;
    private String name;

    // You can include other relevant fields or methods as needed
}
