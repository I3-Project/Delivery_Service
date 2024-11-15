package com.i3.delivery.domain.order.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    private Long id;
    private Long userId;
    private Long addressId;
    private Long productId;
    private Long storeId;
    private Integer quantity;
    private Integer totalPrice;
    private String orderType;
    private Long paymentId;
    private String oRequest;
}
