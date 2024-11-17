package com.i3.delivery.domain.product.controller;

import com.i3.delivery.domain.product.dto.ProductRegistrationRequestDto;
import com.i3.delivery.domain.product.dto.ProductRegistrationResponseDto;
import com.i3.delivery.domain.product.service.ProductService;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAnyAuthority('OWNER', 'MANAGER', 'MASTER')")
    @PostMapping
    public ProductRegistrationResponseDto createStore(@Validated @RequestBody ProductRegistrationRequestDto productRegistrationRequestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return productService.createProduct(userDetails.getUser(), productRegistrationRequestDto);
    }


}