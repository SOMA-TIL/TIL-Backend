package com.til.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "grading-service.api")
@Getter
@Setter
public class GradingApiProperties {

    private String url;
    private String key;
}
