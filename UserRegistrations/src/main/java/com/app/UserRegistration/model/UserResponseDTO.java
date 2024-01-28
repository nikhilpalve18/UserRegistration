package com.app.UserRegistration.model;

import com.app.UserRegistration.entity.Contact;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private List<Contact> contacts;

}
