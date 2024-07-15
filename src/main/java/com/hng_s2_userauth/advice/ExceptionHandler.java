package com.hng_s2_userauth.advice;

import com.hng_s2_userauth.dto.AuthResponseDto;
import com.hng_s2_userauth.exceptions.EmailAlreadyExistException;
import com.hng_s2_userauth.dto.ValidationError;
import com.hng_s2_userauth.exceptions.InvalidCredentialException;
import com.hng_s2_userauth.exceptions.RegistrationUnsuccessfulException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandler {


    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<ValidationError>>> handleMethodArgumentError(MethodArgumentNotValidException err) {
        BindingResult bindingResult = err.getBindingResult();

        List<ValidationError> errorList = bindingResult.getFieldErrors().stream()
                .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        Map<String, List<ValidationError>> errors = new HashMap<>();
        errors.put("errors", errorList);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errors);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @org.springframework.web.bind.annotation.ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<Map<String, List<ValidationError>>> handleEmailAlreadyExistsException(EmailAlreadyExistException ex) {
        ValidationError error = new ValidationError("email", ex.getMessage());

        Map<String, List<ValidationError>> errorResponse = new HashMap<>();
        errorResponse.put("errors", List.of(error));

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }



    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<AuthResponseDto>  handleInvalidLoginCredentials(InvalidCredentialException err) {

        var loginDto= new AuthResponseDto("Bad request", err.getMessage(), 401);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginDto);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(RegistrationUnsuccessfulException.class)
    public ResponseEntity<AuthResponseDto>  handleRegistrationUnsuccessful(RegistrationUnsuccessfulException err) {


        var regdto = new AuthResponseDto("Bad request", err.getMessage(), 400);

        return ResponseEntity.status(400).body(regdto);
    }
}


