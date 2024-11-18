package com.i3.delivery.domain.cart.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRequestDto {
    private Long productId;
    private Long storeId;
    private int quanity;
}
