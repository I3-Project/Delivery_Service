package com.i3.delivery.domain.product.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {

    DELETED("DELETED");

    private final String flag;
}

