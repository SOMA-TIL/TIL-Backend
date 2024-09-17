package com.til.domain.auth.exception;

import com.til.common.exception.BaseException;
import com.til.common.http.response.enums.ErrorCode;

public class TokenInvalidException extends BaseException {

    public TokenInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
