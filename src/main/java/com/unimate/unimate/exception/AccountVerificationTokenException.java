package com.unimate.unimate.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AccountVerificationTokenException extends BaseCustomizedException {

    public AccountVerificationTokenException(String message){
        super(HttpStatus.BAD_REQUEST, message);
    }
}
