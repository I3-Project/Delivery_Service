package com.i3.delivery.global.exception.jwt;


import com.i3.delivery.global.exception.common.BusinessException;
import com.i3.delivery.global.exception.common.ErrorCode;

public class NoJwtException extends BusinessException {

    public NoJwtException() {
        super(ErrorCode.NO_JWT_EXCEPTION);
    }
}
