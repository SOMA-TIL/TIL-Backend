package com.til.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Setter
public class JwtProperties {

    @Getter
    private String secret;

    private Access access;
    private Refresh refresh;

    @Setter
    private static class Access {

        private Long expiration;
    }

    @Setter
    private static class Refresh {

        private Long expiration;
    }

    public Long getAccessExpiration() {
        return access.expiration;
    }

    public Long getRefreshExpiration() {
        return refresh.expiration;
    }
}
