package com.i3.delivery.global.exception.store;

import com.i3.delivery.global.exception.common.BusinessException;
import com.i3.delivery.global.exception.common.ErrorCode;


public class NotStoreManagerOrMaster extends BusinessException {

    public NotStoreManagerOrMaster() {
        super(ErrorCode.NOT_STORE_MANAGER_OR_MASTER);
    }
}