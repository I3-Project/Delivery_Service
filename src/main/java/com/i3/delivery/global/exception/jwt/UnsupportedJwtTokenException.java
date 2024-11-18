package com.i3.delivery.global.exception.jwt;


import com.i3.delivery.global.exception.common.BusinessException;
import com.i3.delivery.global.exception.common.ErrorCode;

public class UnsupportedJwtTokenException extends BusinessException {
    public UnsupportedJwtTokenException() {
        super(ErrorCode.UNSUPPORTED_JWT_TOKEN_EXCEPTION);
    }
}
