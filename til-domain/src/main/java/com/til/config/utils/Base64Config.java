package com.til.config.utils;

import static java.util.Base64.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Base64Config {

    @Bean
    public Encoder base64Encoder() {
        return getEncoder();
    }

}
