package com.til;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;

import com.til.common.http.cors.ReactiveCorsConfig;
import com.til.common.http.security.SecurityWebFluxConfig;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@ComponentScans({
    @ComponentScan("com.til.common"),
    @ComponentScan("com.til.domain.auth"),
    @ComponentScan("com.til.filter"),
})
@Import({SecurityWebFluxConfig.class, ReactiveCorsConfig.class})
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
