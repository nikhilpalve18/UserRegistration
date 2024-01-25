package com.app.UserRegistration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    @NotEmpty(message = "Firstname cannot be empty")
    private String firstName;
    @NotEmpty(message = "Lastname cannot be empty")
    private String lastName;
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @Size(min = 8, message = "Password must be at least of 8 characters")
    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9]).+", message = "Password must contain at least one digit"),
            @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain at least one lowercase letter"),
            @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain at least one uppercase letter"),
            @Pattern(regexp = "(?=.*[!@#$%^&*(),.?\":{}|<>]).+", message = "Password must contain at least one special character")
    })
    private String password;

    @Size(min = 10, max = 10, message = "Mobile number must be 10 digits")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must contain only digits")
    private String mobileNumber;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

}
