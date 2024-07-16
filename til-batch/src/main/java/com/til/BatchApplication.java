package com.til;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BatchApplication {

    // ----Test Code----
    private final TestDomainBean testDomainBean;

    public BatchApplication(TestDomainBean testDomainBean) {
        this.testDomainBean = testDomainBean;
    }

    @PostConstruct
    public void dependencyTest() {
        testDomainBean.dependencyTest();
    }

    // ------------------
    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }
}
