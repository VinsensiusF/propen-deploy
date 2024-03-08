package com.unimate.unimate.aspect;

import com.unimate.unimate.AccountContext;
import com.unimate.unimate.config.AuthConfigProperties;
import com.unimate.unimate.entity.Account;
import com.unimate.unimate.exception.ForbiddenException;
import com.unimate.unimate.service.AccountService;
import com.unimate.unimate.util.JwtUtility;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
@Configuration
public class ValidateTokenAspect {
    private final AccountService accountService;
    private final AuthConfigProperties authConfigProperties;

    @Autowired
    public ValidateTokenAspect(AccountService accountService, AuthConfigProperties authConfigProperties){
        this.accountService = accountService;
        this.authConfigProperties = authConfigProperties;
    }

    @Around("@annotation(com.unimate.unimate.aspect.ValidateToken)")
    public Object validateToken(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = extractTokenFromHeader(request);

        if (token == null) {
            //TODO CHECK
            throw new ForbiddenException();
        }

        //TODO extract account id from token
        Long accountId = JwtUtility.extractAccountId(token, authConfigProperties.getSecret());

        ValidateToken validateTokenAnnotation = getValidateTokenAnnotation(joinPoint);

        // Check if the role is valid
        if (validateTokenAnnotation.value().length != 0 &&
                !JwtUtility.validateRole(token, authConfigProperties.getSecret(), validateTokenAnnotation.value())) {
            throw new ForbiddenException();
        }

        // Load account from the database
        Account account = accountService.getAccountById(accountId);

        // Set the account in the context
        AccountContext.setAccount(account);

        // Continue with the method execution
        return joinPoint.proceed();
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        // Logic to extract token from the header
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Remove "Bearer " prefix
        }

        return null;
    }

    private ValidateToken getValidateTokenAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getAnnotation(ValidateToken.class);
    }
}
