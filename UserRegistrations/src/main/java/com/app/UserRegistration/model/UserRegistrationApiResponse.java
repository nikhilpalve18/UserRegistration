package com.app.UserRegistration.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.Map;

@Data
@AllArgsConstructor
public class UserRegistrationApiResponse {
    private final String message;
    private final Map<String, String> errors;
}