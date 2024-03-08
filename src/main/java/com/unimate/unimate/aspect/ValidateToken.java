package com.unimate.unimate.aspect;

import com.unimate.unimate.enums.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link ValidateTokenAspect}
 *
 * @Description ValidateToken is connecting to the auth service, will invoke request to /verify/token to verify the token
 * @Requirement HttpServletRequest is required in the controller parameter for this to be running
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateToken {
    RoleEnum[] value();
}