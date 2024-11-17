package com.i3.delivery.domain.product.controller;

import com.i3.delivery.domain.product.dto.*;
import com.i3.delivery.domain.product.service.ProductService;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_MANAGER', 'ROLE_MASTER')")
    @PostMapping
    public ProductRegistrationResponseDto createStore(@Validated @RequestBody ProductRegistrationRequestDto productRegistrationRequestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return productService.createProduct(userDetails.getUser(), productRegistrationRequestDto);
    }

    @GetMapping("/{id}")
    public ProductInfoResponseDto getProduct(@PathVariable(name = "id") Long id) {

        return productService.getProduct(id);
    }

    @GetMapping
    public Page<ProductInfoResponseDto> getProductAll(@PageableDefault(page = 0, size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
                                                      @RequestParam Integer size) {

        return productService.getProductAll(pageable,size);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_OWNER', 'ROLE_MASTER')")
    @PatchMapping("/{id}")
    public ProductEditResponseDto updateStore(@PathVariable(name = "id") Long id, @RequestBody ProductEditRequestDto productEditRequsetDto) {

        return productService.updateProduct(id, productEditRequsetDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_OWNER', 'ROLE_MASTER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") Long id) {

        return productService.deleteProduct(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MASTER')")
    @DeleteMapping("/all/{id}")
    public ResponseEntity<String> deleteProductAll(@PathVariable(name = "id") Long id) {

        return productService.deleteProductAll(id);
    }
}