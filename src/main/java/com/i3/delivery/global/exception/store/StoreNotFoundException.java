package com.i3.delivery.global.exception.store;

import com.i3.delivery.global.exception.common.BusinessException;
import com.i3.delivery.global.exception.common.ErrorCode;

public class StoreNotFoundException extends BusinessException {

    public StoreNotFoundException() {
        super(ErrorCode.NOT_FOUND_STORE_EXCEPTION);
    }
}