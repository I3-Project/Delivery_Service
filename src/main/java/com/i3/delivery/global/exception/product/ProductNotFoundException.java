package com.i3.delivery.global.exception.product;

import com.i3.delivery.global.exception.common.BusinessException;
import com.i3.delivery.global.exception.common.ErrorCode;

public class ProductNotFoundException extends BusinessException {

    public ProductNotFoundException() {
        super(ErrorCode.NOT_FOUND_PRODUCT_EXCEPTION);
    }
}