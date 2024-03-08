package com.unimate.unimate.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForbiddenException extends BaseCustomizedException {
    public ForbiddenException() {
        super(HttpStatus.BAD_REQUEST, "Token not found in the request header.");
    }
}
