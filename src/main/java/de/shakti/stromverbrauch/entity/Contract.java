package de.shakti.stromverbrauch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;

@Data
@Document("contract")
public class Contract {

    @Id
    private Long id;
    @Email
    private String userEmail;
}

