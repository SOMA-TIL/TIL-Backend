package com.til;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.til.common.http.cors.BasicCorsConfig;
import com.til.common.http.errorhandling.GlobalExceptionHandler;
import com.til.common.http.security.SecurityConfig;
import com.til.config.DiscoveryClientConfig;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@Import({DiscoveryClientConfig.class, SecurityConfig.class, GlobalExceptionHandler.class, BasicCorsConfig.class})
public class ApiApplication {

    // ----Test Code----
    private final TestDomainBean testDomainBean;

    public ApiApplication(TestDomainBean testDomainBean) {
        this.testDomainBean = testDomainBean;
    }

    @PostConstruct
    public void dependencyTest() {
        testDomainBean.dependencyTest();
    }
    // ------------------

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
