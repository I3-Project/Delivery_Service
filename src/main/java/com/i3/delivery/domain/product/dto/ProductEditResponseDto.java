package com.i3.delivery.domain.product.dto;

import com.i3.delivery.domain.product.entity.Product;
import com.i3.delivery.domain.product.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEditResponseDto {

    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private ProductStatus status;
    private LocalDateTime createAt;
    private String createBy;


    public static ProductEditResponseDto fromEntity(Product product) {
        return ProductEditResponseDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .status(product.getStatus())
                .createAt(product.getCreatedAt())
                .createBy(product.getCreatedBy())
                .build();
    }
}
