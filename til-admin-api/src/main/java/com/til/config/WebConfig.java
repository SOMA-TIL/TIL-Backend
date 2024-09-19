package com.til.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import com.til.common.http.cors.BasicCorsConfig;
import com.til.config.resolver.CurrentAdminResolver;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig extends BasicCorsConfig {

    private final CurrentAdminResolver currentAdminResolver;

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentAdminResolver);
    }
}
