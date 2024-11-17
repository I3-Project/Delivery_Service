package com.i3.delivery.global.exception.category;

import com.i3.delivery.global.exception.common.BusinessException;
import com.i3.delivery.global.exception.common.ErrorCode;

public class AlreadyExistCategoryException extends BusinessException {

    public AlreadyExistCategoryException() {
        super(ErrorCode.ALREADY_EXIST_CATEGORY_NAME_EXCEPTION);
    }
}