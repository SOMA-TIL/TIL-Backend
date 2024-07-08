package com.til.domain.auth.exception;

import com.til.domain.common.enums.ErrorCode;
import com.til.domain.common.exception.BaseException;

public class TokenInvalidException extends BaseException {
	private ErrorCode errorCode;

	public TokenInvalidException(ErrorCode errorCode) {
		super(errorCode);
	}
}
