package com.i3.delivery.domain.order.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderListResponseDto {
    private UUID orderListId;
    private UUID orderId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}
