package com.app.UserRegistration.model;

import com.app.UserRegistration.entity.Contact;
import com.app.UserRegistration.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {

    @Valid
    private User user;

}
