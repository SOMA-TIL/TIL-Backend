package com.til.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.til.common.http.resolver.CurrentUserResolver;

@Configuration
public class ResolverConfig {

    @Bean
    public CurrentUserResolver currentUserResolver() {
        return new CurrentUserResolver();
    }
}
