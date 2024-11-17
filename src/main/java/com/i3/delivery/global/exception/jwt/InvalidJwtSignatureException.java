package com.i3.delivery.global.exception.jwt;


import com.i3.delivery.global.exception.common.BusinessException;
import com.i3.delivery.global.exception.common.ErrorCode;

public class InvalidJwtSignatureException extends BusinessException {
    public InvalidJwtSignatureException() {
        super(ErrorCode.INVALID_JWT_SIGNATURE_EXCEPTION);
    }
}
