package com.i3.delivery.domain.order.dto.response;

import com.i3.delivery.domain.order.dto.request.OrderRequestDto;
import com.i3.delivery.domain.order.dto.response.OrderListResponseDto;
import com.i3.delivery.domain.order.entity.Order;
import com.i3.delivery.domain.order.entity.enums.OrderStatusEnum;
import com.i3.delivery.domain.order.entity.enums.OrderTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {

    private UUID orderId;
    private Long userId;
    private UUID addressId;
    private UUID productId;
    private UUID storeId;
    private OrderTypeEnum orderType;
    private OrderStatusEnum orderStatus;
    private UUID paymentId;
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
