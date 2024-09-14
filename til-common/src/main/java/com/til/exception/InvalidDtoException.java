package com.til.exception;

public class InvalidDtoException extends RuntimeException {

    public InvalidDtoException(String message) {
        super(message);
    }
}
