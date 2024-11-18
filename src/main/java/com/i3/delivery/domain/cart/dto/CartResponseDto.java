package com.i3.delivery.domain.cart.dto;

import com.i3.delivery.domain.cart.entity.Cart;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDto {
    private Long id;
    private Long userId;
    private Long productId;
    private Long storeId;
    private int quantity;

    public CartResponseDto(Cart cart) {
        this.id = cart.getId();
        this.userId = cart.getUser().getId();
        this.productId = cart.getProduct().getId();
        this.storeId = cart.getStore().getId();
        this.quantity = cart.getQuantity();
    }
}
