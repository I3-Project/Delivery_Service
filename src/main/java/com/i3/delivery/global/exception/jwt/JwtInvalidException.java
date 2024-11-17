package com.i3.delivery.global.exception.jwt;


import com.i3.delivery.global.exception.common.BusinessException;
import com.i3.delivery.global.exception.common.ErrorCode;

public class JwtInvalidException extends BusinessException {

    public JwtInvalidException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
