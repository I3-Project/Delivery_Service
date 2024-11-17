package com.i3.delivery.global.exception.jwt;


import com.i3.delivery.global.exception.common.BusinessException;
import com.i3.delivery.global.exception.common.ErrorCode;

public class ExpiredJwtTokenException extends BusinessException {
    public ExpiredJwtTokenException() {
        super(ErrorCode.EXPIRED_JWT_TOKEN_EXCEPTION);
    }
}
