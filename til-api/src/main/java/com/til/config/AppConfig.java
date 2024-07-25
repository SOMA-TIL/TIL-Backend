package com.til.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class AppConfig {

    @Getter
    @Value("${jwt.filter.enabled:true}")
    private boolean jwtFilterEnabled;

}
