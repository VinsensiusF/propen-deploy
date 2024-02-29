package com.unimate.unimate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "unimate.auth")
@Component
@Data
public class AuthConfigProperties {
    private String secret;

    //This field will contain FE page URL tasked to do email token verification
    private String feEmailVerificationURL;
}
