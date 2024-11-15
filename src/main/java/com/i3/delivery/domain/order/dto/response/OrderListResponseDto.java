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
    private Long productId;
    //private Long ownerId;
    private String productName;
    private Integer quantity;
    private BigDecimal totalPrice;
    private OrderStatusEnum orderState;
    private String oRequest;
    private LocalDateTime createdAt;
    private String createdBy;

    public OrderListResponseDto(Order order) {
            this.userId = order.getUser().getId();
            this.orderId = order.getId();
            this.productId = order.getProduct_id();
            //this.ownerId = order.getOwnerId();
            this.quantity = order.getQuantity();
            this.orderState = order.getOrderStatus();
            this.oRequest = order.getORequest();
            this.productName = order.getProductName();
            this.totalPrice = order.getTotalPrice();
            this.createdAt = order.getCreatedAt();
    }


}
