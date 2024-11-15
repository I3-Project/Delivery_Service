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
    private Long id;
    private Long userId;
    private Long addressId;
    private Long productId;
    private Long storeId;
    private Integer quantity;
    private Integer totalPrice;
    private String orderType;
    private UUID paymentId;
    private String oRequest;
    private List<OrderProductListDto> productList;
}
