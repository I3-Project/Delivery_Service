package com.i3.delivery.domain.order.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderProductListDto {
    private UUID productId;
    private Integer quantity;
}
