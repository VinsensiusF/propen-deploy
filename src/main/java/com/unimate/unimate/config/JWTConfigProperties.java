package com.unimate.unimate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "unimate.jwt")
@Data
public class JWTConfigProperties {
    private String secret;
}
