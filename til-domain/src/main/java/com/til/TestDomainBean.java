package com.til;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TestDomainBean {

    public void dependencyTest() {
        log.info("성공적으로 til-domain 모듈이 로딩되었습니다.");
    }
}
