package com.i3.delivery.global.exception.category;

import com.i3.delivery.global.exception.common.BusinessException;
import com.i3.delivery.global.exception.common.ErrorCode;

public class CategoryNotFoundException extends BusinessException {

    public CategoryNotFoundException() {
        super(ErrorCode.NOT_FOUND_CATEGORY_EXCEPTION);
    }
}
