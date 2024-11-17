package com.i3.delivery.global.exception.jwt;


import com.i3.delivery.global.exception.common.BusinessException;
import com.i3.delivery.global.exception.common.ErrorCode;

public class UnsupportedGrantTypeException extends BusinessException {

    public UnsupportedGrantTypeException() {
        super(ErrorCode.NOT_SUPPORTED_GRANT_TYPE_EXCEPTION);
    }
}
