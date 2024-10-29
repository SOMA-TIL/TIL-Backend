package com.til.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "llm-service.api")
@Getter
@Setter
public class LlmApiProperties {

    private String url;
    private String key;
}
