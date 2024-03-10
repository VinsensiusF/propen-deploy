package com.unimate.unimate;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class AspectHelper {
    public static HttpServletRequest getHttpServletRequest(ProceedingJoinPoint joinPoint) {
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        return args.stream()
                .filter(HttpServletRequest.class::isInstance)
                .findFirst()
                .map(HttpServletRequest.class::cast)
                .orElse(null);
    }

}