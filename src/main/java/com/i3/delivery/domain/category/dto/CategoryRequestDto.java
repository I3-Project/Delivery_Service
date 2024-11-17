package com.i3.delivery.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequestDto {

    @NotBlank(message = "카테고리 명을 입력해주세요")
    private String categoryName;

    @NotBlank(message = "설명을 입력해주세요")
    private String description;
}
