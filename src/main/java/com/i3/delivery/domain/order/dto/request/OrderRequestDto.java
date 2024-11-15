package com.i3.delivery.domain.order.dto.request;

import com.i3.delivery.domain.order.dto.response.OrderProductListDto;
import com.i3.delivery.domain.order.entity.enums.OrderTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderRequestDto {
    private UUID id;
    private UUID userId;
    private UUID addressId;
    private UUID productId;
    private UUID storeId;
    private Integer quantity;
    private Integer totalPrice;
    private OrderTypeEnum orderType;
    private UUID paymentId;
    private List<OrderProductListDto> productList;
    private String createdBy;
    private String updatedBy;
    private String deletedBy;
}