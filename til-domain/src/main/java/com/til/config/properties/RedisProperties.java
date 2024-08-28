package com.til.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
@Getter
@Setter
public class RedisProperties {

    private String host;
    private int port;
    private String password;
}
