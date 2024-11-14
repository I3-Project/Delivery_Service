package com.i3.delivery.domain.order.dto.response;

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
    private UUID userId;
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
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private LocalDateTime deletedAt;
    private String deletedBy;

    public static OrderResponseDto reResponseDto(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .userId(order.getUser().getUserId())
                .addressId(order.getAddress().getId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .amount(order.getTotalPrice())
                .type(order.getOrderType())
                .createdAt(order.getCreatedAt())
                .createdBy(order.getCreatedBy())
                .updatedAt(order.getUpdatedAt())
                .updatedBy(order.getUpdatedBy())
                .build();
    }


}
