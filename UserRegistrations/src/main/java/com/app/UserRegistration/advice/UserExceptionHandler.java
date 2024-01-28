package com.app.UserRegistration.advice;

import com.app.UserRegistration.exception.UserAlreadyPresentException;
import com.app.UserRegistration.exception.UserNotFoundException;
import com.app.UserRegistration.model.UserRegistrationApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public UserRegistrationApiResponse handleInvalidInputs(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(error->{
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new UserRegistrationApiResponse("Validation error", errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public UserRegistrationApiResponse handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );
        return new UserRegistrationApiResponse("Validation error", errors);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserAlreadyPresentException.class)
    public UserRegistrationApiResponse handleUserAlreadyPresent(UserAlreadyPresentException userAlreadyPresentException){
        return new UserRegistrationApiResponse(userAlreadyPresentException.getMessage(),null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserNotFoundException.class)
    public UserRegistrationApiResponse handleUserNotFound(UserNotFoundException userNotFoundException){
        return new UserRegistrationApiResponse(userNotFoundException.getMessage(), null);
    }
}
