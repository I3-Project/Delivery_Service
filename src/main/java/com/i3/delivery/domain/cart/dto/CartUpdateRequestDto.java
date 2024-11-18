package com.i3.delivery.domain.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartUpdateRequestDto {
    private Long productId;
    private int quantity;
}
