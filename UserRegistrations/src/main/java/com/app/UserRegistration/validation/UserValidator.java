package com.app.UserRegistration.validation;

import com.app.UserRegistration.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;


@Validated
@Component
public class UserValidator {
    private final Validator validator;

    public UserValidator() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public <T> void validate(T obj) throws ValidationException {
        Set<ConstraintViolation<T>> violations = validator.validate(obj);
        if (!violations.isEmpty()) {
            String errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("; ")); // Join error messages with a separator
            throw new ValidationException(errorMessages);
        }
    }
}
