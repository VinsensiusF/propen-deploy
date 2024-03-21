package com.unimate.unimate.exception;

import org.springframework.http.HttpStatus;

public class CourseNotFoundException extends BaseServiceException {
    public static final String DEFAULT_MESSAGE = "Course not found!";
    public static final String DEFAULT_TITLE = CourseNotFoundException.class.getSimpleName();
    public static final int DEFAULT_HTTP_CODE = HttpStatus.NOT_FOUND.value();

    public CourseNotFoundException() {
        super(DEFAULT_MESSAGE, DEFAULT_TITLE, DEFAULT_HTTP_CODE);
    }
}
