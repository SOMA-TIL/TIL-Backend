package com.til.http.response.enums;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String name();

    String getMessage();

    HttpStatus getStatus();
}
