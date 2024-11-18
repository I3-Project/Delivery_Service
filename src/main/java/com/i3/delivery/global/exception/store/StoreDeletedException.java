package com.i3.delivery.global.exception.store;

import com.i3.delivery.global.exception.common.BusinessException;
import com.i3.delivery.global.exception.common.ErrorCode;

public class StoreDeletedException extends BusinessException {

    public StoreDeletedException() {
        super(ErrorCode.DELETED_STORE_EXCEPTION);
    }
}