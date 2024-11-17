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
public class ProductInfoResponseDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private ProductStatus status;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private LocalDateTime deletedAt;
    private String deletedBy;

    public static ProductInfoResponseDto fromEntity(Product product) {
        return ProductInfoResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .createdBy(product.getCreatedBy())
                .updatedAt(product.getUpdatedAt())
                .updatedBy(product.getUpdatedBy())
                .deletedAt(product.getDeletedAt())
                .deletedBy(product.getDeletedBy())
                .build();
    }
}
