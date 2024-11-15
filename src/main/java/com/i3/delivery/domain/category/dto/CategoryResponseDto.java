package com.i3.delivery.domain.category.dto;

import com.i3.delivery.domain.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDto {

    private String CategoryName;
    private String description;

    public static CategoryResponseDto fromEntity(Category savedCategory) {

        return CategoryResponseDto.builder()
                .CategoryName(savedCategory.getName())
                .description(savedCategory.getDescription())
                .build();

    }
}
