package com.i3.delivery.domain.category.service;

import com.i3.delivery.domain.category.dto.CategoryRequestDto;
import com.i3.delivery.domain.category.dto.CategoryResponseDto;
import com.i3.delivery.domain.category.entity.Category;
import com.i3.delivery.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {

        Category category = Category.builder()
                .name(categoryRequestDto.getCategoryName())
                .description(categoryRequestDto.getDescription())
                .build();

        Category savedCategory = categoryRepository.save(category);
        return CategoryResponseDto.fromEntity(savedCategory);
    }

    public Category findCategory(Long categoryId) {

        return categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
    }
}