package com.unimate.unimate.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BaseServiceException {
    public static final String DEFAULT_MESSAGE = "Account not found!";
    public static final String DEFAULT_TITLE = EntityNotFoundException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public EntityNotFoundException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}
