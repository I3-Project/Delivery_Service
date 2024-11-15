package com.i3.delivery.domain.order.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatusEnum {
    PENDING("ORDER_STATUS_PENDING"),
    DELIVERY("ORDER_STATUS_DELIVERY"),
    COMPLETED("ORDER_STATUS_COMPLETED"),
    CANCELED("ORDER_STATUS_CANCELED"),
    DELETED("ORDER_STATUS_DELETED");

    private final String status;
}
