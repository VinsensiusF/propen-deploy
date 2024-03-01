package com.unimate.unimate.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Jwt utility to Generate and Verify JWT for authentication purpose
 * Secret is referring to {@link JWTConfigProperties}
 */
@UtilityClass
public class JwtUtility {

    public static String jwtGenerator(Long id, String secret) throws JWTCreationException {
        return JWT.create()
                .withExpiresAt(Instant.now().plus(100000000, ChronoUnit.MINUTES))
                .withJWTId(String.valueOf(id))
                .withIssuer("auth0")
                .sign(Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8)));
    }

    public static DecodedJWT jwtVerifier(String token, String secret) throws JWTVerificationException {
        if (token.startsWith("Bearer ")) {
            token = token.split("Bearer ")[1];
        }
        final Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
        final JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
        return verifier.verify(token);
    }
}
