package com.i3.delivery.domain.order.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    private Long id;
    private Long userId;
    private Long storeId;
    private String orderRequest;
    private String orderType;
}
