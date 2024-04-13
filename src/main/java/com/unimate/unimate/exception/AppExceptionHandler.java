package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(EmailSentException.class)
    public ResponseEntity<CustomErrorResponse> EmailSentException(EmailSentException emailSentException){
        return new ResponseEntity<>(emailSentException.generateCustomErrorResponse(), HttpStatus.valueOf(emailSentException.getHttpStatusCode()));
    }

    @ExceptionHandler(EmailSendFailedException.class)
    public ResponseEntity<CustomErrorResponse> EmailSendFailedException(EmailSendFailedException emailSendFailedException){
        return new ResponseEntity<>(emailSendFailedException.generateCustomErrorResponse(), HttpStatus.valueOf(emailSendFailedException.getHttpStatusCode()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> EntityNotFoundException(EntityNotFoundException entityNotFoundException){
        return new ResponseEntity<>(entityNotFoundException.generateCustomErrorResponse(), HttpStatus.valueOf(entityNotFoundException.getHttpStatusCode()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<CustomErrorResponse> InvalidTokenException(InvalidTokenException invalidEmailException){
        return new ResponseEntity<>(invalidEmailException.generateCustomErrorResponse(), HttpStatus.valueOf(invalidEmailException.getHttpStatusCode()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CustomErrorResponse> ForbiddenException(ForbiddenException forbiddenException){
        return new ResponseEntity<>(forbiddenException.generateCustomErrorResponse(), HttpStatus.valueOf(forbiddenException.getHttpStatusCode()));
    }

    @ExceptionHandler(VerifiedUserException.class)
    public ResponseEntity<CustomErrorResponse> VerifiedUserException(VerifiedUserException verifiedUserException){
        return new ResponseEntity<>(verifiedUserException.generateCustomErrorResponse(), HttpStatus.valueOf(verifiedUserException.getHttpStatusCode()));
    }

    @ExceptionHandler(UnverifiedUserException.class)
    public ResponseEntity<CustomErrorResponse> UnverifiedUserException(UnverifiedUserException unverifiedUserException){
        return new ResponseEntity<>(unverifiedUserException.generateCustomErrorResponse(), HttpStatus.valueOf(unverifiedUserException.getHttpStatusCode()));
    }

    @ExceptionHandler(AccountExistedException.class)
    public ResponseEntity<CustomErrorResponse> AccountExistedException(AccountExistedException accountExistedException){
        return new ResponseEntity<>(accountExistedException.generateCustomErrorResponse(), HttpStatus.valueOf(accountExistedException.getHttpStatusCode()));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<CustomErrorResponse> InvalidTokenException(InvalidPasswordException InvalidPasswordException){
        return new ResponseEntity<>(InvalidPasswordException.generateCustomErrorResponse(), HttpStatus.valueOf(InvalidPasswordException.getHttpStatusCode()));
    }

    @ExceptionHandler(KelasNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> KelasNotFoundException(KelasNotFoundException kelasNotFoundException) {
        return new ResponseEntity<>(kelasNotFoundException.generateCustomErrorResponse(), HttpStatus.valueOf(kelasNotFoundException.getHttpStatusCode()));
    }

    @ExceptionHandler(UjianNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> UjianNotFound(UjianNotFoundException ujianNotFound) {
        return new ResponseEntity<>(ujianNotFound.generateCustomErrorResponse(), HttpStatus.valueOf(ujianNotFound.getHttpStatusCode()));
    }

    @ExceptionHandler(UjianSiswaNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> UjianNotFound(UjianSiswaNotFoundException ujianSiswaNotFoundException) {
        return new ResponseEntity<>(ujianSiswaNotFoundException.generateCustomErrorResponse(), HttpStatus.valueOf(ujianSiswaNotFoundException.getHttpStatusCode()));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> UjianNotFound(AccountNotFoundException accountNotFoundException) {
        return new ResponseEntity<>(accountNotFoundException.generateCustomErrorResponse(), HttpStatus.valueOf(accountNotFoundException.getHttpStatusCode()));
    }
}
