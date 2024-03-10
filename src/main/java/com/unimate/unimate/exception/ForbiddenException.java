package com.unimate.unimate.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForbiddenException extends BaseServiceException {
//    public ForbiddenException() {
//        super(HttpStatus.BAD_REQUEST, "Token not found in the request header.");
//    }

    public static final String DEFAULT_MESSAGE = "Token is not validated or Unauthorized Role.";
    public static final String DEFAULT_TITLE = EntityNotFoundException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public ForbiddenException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}
