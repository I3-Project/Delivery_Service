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
    private Long addressId;
    private Long productId;
    private Long storeId;
    private OrderTypeEnum orderType;
    private OrderStatusEnum orderStatus;
    private Long paymentId;
    private String oRequest;
    private Integer quantity;
    private BigDecimal totalPrice;
    private List<OrderListResponseDto> orderList;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.userId = order.getUser().getId();
        this.storeId = order.getStore().getId();
        this.orderType = order.getOrderType();
        this.orderStatus = order.getOrderStatus();
        this.totalPrice = order.getTotalPrice();
        this.oRequest = order.getORequest();
    }
}
