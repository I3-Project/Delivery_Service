package com.i3.delivery.domain.category.controller;

import com.i3.delivery.domain.category.dto.CategoryRequestDto;
import com.i3.delivery.domain.category.dto.CategoryResponseDto;
import com.i3.delivery.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasAnyAuthority('MANAGER', 'MASTER')")
    @PostMapping
    public CategoryResponseDto createCategory(@Validated @RequestBody CategoryRequestDto categoryRequestDto) {

        return categoryService.createCategory(categoryRequestDto);
    }
}
