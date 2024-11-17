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
public class ProductRegistrationResponseDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private Long storeId;
    private String storeName;
    private ProductStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductRegistrationResponseDto fromEntity(Product product) {
        return ProductRegistrationResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .storeId(product.getStore().getId())
                .storeName(product.getStore().getName())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
