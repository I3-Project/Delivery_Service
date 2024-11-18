package com.i3.delivery.global.exception.jwt;


import com.i3.delivery.global.exception.common.BusinessException;
import com.i3.delivery.global.exception.common.ErrorCode;

public class InvalidJwtException extends BusinessException {
    public InvalidJwtException() {
        super(ErrorCode.INVALID_JWT_EXCEPTION);
    }
}
