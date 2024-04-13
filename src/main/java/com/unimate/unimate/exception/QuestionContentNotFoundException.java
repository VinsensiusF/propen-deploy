package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class QuestionContentNotFoundException extends BaseServiceException {
    public static final String DEFAULT_MESSAGE = "QuestionContent not found.";
    public static final String DEFAULT_TITLE = QuestionContentNotFoundException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public QuestionContentNotFoundException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}
