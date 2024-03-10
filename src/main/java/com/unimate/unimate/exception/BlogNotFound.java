package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class BlogNotFound extends BaseServiceException{
    public static final String DEFAULT_MESSAGE = "Blog is not found.";
    public static final String DEFAULT_TITLE = BlogNotFound.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST.value();

    public BlogNotFound() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    };
}