package com.unimate.unimate;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ValidateTokenAspect {
//    private static final String UNAUTHORIZED = "Unauthorized";
//    private final BaseAuthService baseAuthService;
//
//    @Value("${spring.data.redis.environment:#{'local'}}")
//    public String env;
//    @Value("${spring.data.redis.time-to-live:#{300}}")
//    public long ttl;
//    @Value("${app.csrf.enabled:false}")
//    private boolean isCsrfEnabled;
//
//    @Autowired
//    public ValidateTokenAspect(
//            BaseAuthService baseAuthService
//    ) {
//        this.baseAuthService = baseAuthService;
//    }
//
//    @Around("@annotation(sg.customs.ntp.rsrf.commonservice.auth.ValidateToken)")
//    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
//        log.info("ValidateTokenAspect validating....");
//        HttpServletRequest httpServletRequest = AspectHelper.getHttpServletRequest(joinPoint);
//
//        ValidateToken validateToken = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(ValidateToken.class);
//
//        log.info("Attempting to fetch headers");
//
//        Optional<String> tokenFromHeader = Optional.ofNullable(httpServletRequest.getHeader(ApiConstants.AUTHORIZATION));
//        log.info("Headers retrieved");
//
//        // throws exception if they both are true
//        validateAccessTokenMissing(httpServletRequest, className, isExternalHeaderExist, tokenFromHeader.isEmpty());
//
//        Optional<Cookie[]> cookies = Optional.ofNullable(httpServletRequest.getCookies());
//
//        Optional<Cookie> authCookie = cookies.flatMap(value -> Arrays.stream(value)
//                .filter(cookie -> Objects.equals(cookie.getName(), ApiConstants.AUTH_COOKIE))
//                .findFirst());
//
//        // throws exception if they both are true
//        validateUnauthorizedAccess(httpServletRequest, className, isExternalHeaderExist, authCookie.isPresent());
//
//        Optional<String> tokenFromCookie = authCookie.map(Cookie::getValue);
//
//        Optional<String> token = tokenFromCookie.isPresent()
//                ? tokenFromCookie.map(s -> ApiConstants.BEARER_TOKEN + " " + s)
//                : tokenFromHeader;
//
//        if (token.isEmpty()) {
//            log.info("Token is empty");
//        }
//
//        log.info("Basic validity check of JWT token");
//        validateJwtToken(token.get());
//        log.info("JWT token basic check done");
//
//        log.info("Attempting DB Token Validation");
//        //todo get user information
//
//
//        validateUnauthorizedAccess(httpServletRequest, className, isExternalHeaderExist);
//
//        if (isApiUser && !isExternalHeaderExist) {
//            throw new CustomErrorException(
//                    HttpStatus.UNAUTHORIZED,
//                    UNAUTHORIZED,
//                    new ValidationTransformerModel(
//                            httpServletRequest,
//                            className,
//                            FieldConstants.TOKEN_TYPE,
//                            user.getTokenType(),
//                            UNAUTHORIZED
//                    )
//            );
//        }
//
//        log.info("ValidateTokenAspect validation success");
//
//        return joinPoint.proceed();
//    }
//
//    private void validateJwtToken(String token) {
//
//
//    }
}