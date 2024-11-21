package com.til.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@ConditionalOnProperty(name = "eureka.client.enabled", havingValue = "true", matchIfMissing = false)
@EnableDiscoveryClient
public class DiscoveryClientConfig {

}
