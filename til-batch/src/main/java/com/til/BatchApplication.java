package com.til;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchApplication {
    // ----Test Code----
    private final TestBean testBean;

    @Autowired
    public BatchApplication(TestBean testBean) {
        this.testBean = testBean;
    }

    @PostConstruct
    public void dependencyTest() {
        testBean.dependencyTest();
    }
    // ------------------

    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }
}