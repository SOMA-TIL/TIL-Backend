package com.til.common.config.helper;

import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RandomConfig {

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }

}
