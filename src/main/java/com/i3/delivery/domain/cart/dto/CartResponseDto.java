package com.i3.delivery.domain.cart.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDto {
    private Long id;
    private String uuid;
    private Long userId;
    private Long productId;
    private Long storeId;
    private int quantity;
}
