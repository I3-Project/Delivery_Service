package com.i3.delivery.domain.order.dto.response;

import com.i3.delivery.domain.order.entity.Order;
import com.i3.delivery.domain.order.entity.enums.OrderStatusEnum;
import com.i3.delivery.domain.order.entity.enums.OrderTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {

    private Long orderId;
    private Long userId;
    private Long storeId;
    private String address;
    private OrderTypeEnum orderType;
    private OrderStatusEnum orderStatus;
    private BigDecimal totalPrice;
    private String orderRequest;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.userId = order.getUser().getId();
        this.storeId = order.getStore().getId();
        this.address = order.getAddress();
        this.orderType = order.getOrderType();
        this.orderStatus = order.getOrderStatus();
        this.totalPrice = order.getTotalPrice();
        this.orderRequest = order.getOrderRequest();
    }
}
