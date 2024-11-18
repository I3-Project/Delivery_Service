package com.i3.delivery.domain.order.dto.response;

import com.i3.delivery.domain.order.entity.Order;
import com.i3.delivery.domain.order.entity.enums.OrderStatusEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderListResponseDto {
    private Long userId;
    private Long orderId;
    private BigDecimal totalPrice;
    private OrderStatusEnum orderState;
    private String orderRequest;
    private LocalDateTime createdAt;

    public OrderListResponseDto(Order order) {
            this.userId = order.getUser().getId();
            this.orderId = order.getId();
            this.orderState = order.getOrderStatus();
            this.orderRequest = order.getOrderRequest();
            this.totalPrice = order.getTotalPrice();
            this.createdAt = order.getCreatedAt();
    }


}
