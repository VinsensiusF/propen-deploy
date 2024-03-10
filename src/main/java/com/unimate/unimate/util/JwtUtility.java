package com.unimate.unimate.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.unimate.unimate.entity.Role;
import com.unimate.unimate.enums.RoleEnum;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Jwt utility to Generate and Verify JWT for authentication purpose
 * Secret is referring to {@link com.unimate.unimate.config.JWTConfigProperties}
 */
@UtilityClass
public class JwtUtility {

    public String jwtGenerator(Long id, String secret, RoleEnum role) throws JWTCreationException {
        ArrayList<String> roles = new ArrayList<>();
        roles.add(role.toString());
        return JWT.create()
                .withExpiresAt(Instant.now().plus(100000000, ChronoUnit.MINUTES))
                .withJWTId(String.valueOf(id))
                .withIssuer("auth0")
                .withClaim("roles", roles)
                .sign(Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8)));
    }

    public DecodedJWT jwtVerifier(String token, String secret) throws JWTVerificationException {
        if (token.startsWith("Bearer ")) {
            token = token.split("Bearer ")[1];
        }
        final Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
        final JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
        return verifier.verify(token);
    }

    public Long extractAccountId(String token, String secret) throws JWTVerificationException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .verify(token);

        return Long.parseLong(decodedJWT.getId());
    }

    public boolean validateRole(String token, String secret,  RoleEnum... roles) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .verify(token);
         String[] tokenRoles = decodedJWT.getClaim("roles").asArray(String.class);
         return Arrays.stream(roles).anyMatch(role -> Arrays.asList(tokenRoles).contains(role.name()));
    }
}
