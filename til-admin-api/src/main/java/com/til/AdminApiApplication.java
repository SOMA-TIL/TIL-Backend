package com.til;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.til.common.http.errorhandling.GlobalExceptionHandler;
import com.til.common.http.security.SecurityConfig;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
public class AdminApiApplication {

    // ----Test Code----
    private final TestDomainBean testDomainBean;

    public AdminApiApplication(TestDomainBean testDomainBean) {
        this.testDomainBean = testDomainBean;
    }

    @PostConstruct
    public void dependencyTest() {
        testDomainBean.dependencyTest();
    }

    // ------------------
    public static void main(String[] args) {
        SpringApplication.run(AdminApiApplication.class, args);
    }
}
