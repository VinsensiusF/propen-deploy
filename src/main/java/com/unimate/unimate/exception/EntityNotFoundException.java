package com.unimate.unimate.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotFoundException extends BaseCustomizedException {
    public EntityNotFoundException(Class<?> clazz) {
        super(HttpStatus.BAD_REQUEST, clazz.getSimpleName() + " not found");
    }
}
