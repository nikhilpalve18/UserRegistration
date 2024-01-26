package com.app.UserRegistration.advice;

import com.app.UserRegistration.exception.UserAlreadyPresentException;
import com.app.UserRegistration.exception.UserNotFoundException;
import com.app.UserRegistration.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleInvalidInputs(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(error->{
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ApiResponse(HttpStatus.BAD_REQUEST, "Validation error", errors);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserAlreadyPresentException.class)
    public ApiResponse handleUserAlreadyPresent(UserAlreadyPresentException userAlreadyPresentException){
        return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, userAlreadyPresentException.getMessage(),null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse handleUserNotFound(UserNotFoundException userNotFoundException){
        return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, userNotFoundException.getMessage(), null);
    }
}
