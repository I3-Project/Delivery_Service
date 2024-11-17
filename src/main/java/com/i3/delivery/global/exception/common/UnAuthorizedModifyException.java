package com.i3.delivery.global.exception.common;

public class UnAuthorizedModifyException extends BusinessException{

    public UnAuthorizedModifyException() {
        super(ErrorCode.UNAUTHORIZED_MODIFY_EXCEPTION);
    }
}
