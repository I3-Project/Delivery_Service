package com.i3.delivery.domain.payment.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatusEnum {
    PENDING("PAYMENT_PENDING"),
    COMPLETED("PAYMENT_COMPLETED"),
    CANCELED("PAYMENT_CANCELED");

    private final String status;
}
