package com.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "emails")
public class Email {

    @Id
    private String id;
    private String message;
    private String result;
    private LocalDateTime timestamp = LocalDateTime.now();

    public Email() {}

    public Email(String message, String result) {
        this.message = message;
        this.result = result;
    }
}
