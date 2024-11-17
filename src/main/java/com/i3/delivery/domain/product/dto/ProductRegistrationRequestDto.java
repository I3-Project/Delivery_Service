package com.i3.delivery.domain.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProductRegistrationRequestDto {

    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private Long storeId;
}
