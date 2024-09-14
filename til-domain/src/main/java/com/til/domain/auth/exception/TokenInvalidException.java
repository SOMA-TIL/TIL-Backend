package com.til.domain.auth.exception;

import com.til.exception.BaseException;
import com.til.http.response.enums.ErrorCode;

public class TokenInvalidException extends BaseException {

    public TokenInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
