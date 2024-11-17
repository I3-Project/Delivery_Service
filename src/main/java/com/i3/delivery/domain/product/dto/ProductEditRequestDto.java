package com.i3.delivery.domain.product.dto;

import com.i3.delivery.domain.product.enums.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProductEditRequestDto {

    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private ProductStatus status;
}
