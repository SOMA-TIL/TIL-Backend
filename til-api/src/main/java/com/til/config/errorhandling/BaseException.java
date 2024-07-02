package com.til.config.errorhandling;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
	private ErrorCode errorCode;
	
	public BaseException(ErrorCode e) {
		super(e.getMessage());
		this.errorCode = e;
	}
}
