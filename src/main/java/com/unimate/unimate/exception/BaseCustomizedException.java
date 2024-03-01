package com.unimate.unimate.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseCustomizedException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;

    public BaseCustomizedException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
