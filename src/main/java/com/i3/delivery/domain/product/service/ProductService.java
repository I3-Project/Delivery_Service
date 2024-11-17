package com.i3.delivery.domain.product.service;

import com.i3.delivery.domain.product.dto.*;
import com.i3.delivery.domain.product.entity.Product;
import com.i3.delivery.domain.product.enums.ProductStatus;
import com.i3.delivery.domain.product.repository.ProductRepository;
import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.store.service.StoreService;
import com.i3.delivery.domain.user.entity.User;
import com.i3.delivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StoreService storeService;

    public ProductRegistrationResponseDto createProduct(User user, ProductRegistrationRequestDto productRegistrationRequestDto) {

        User productUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));

        Store store = storeService.findStore(productRegistrationRequestDto.getStoreId());

        Product product = Product.builder()
                .name(productRegistrationRequestDto.getName())
                .description(productRegistrationRequestDto.getDescription())
                .price(productRegistrationRequestDto.getPrice())
                .stock(productRegistrationRequestDto.getStock())
                .store(store)
                .status(ProductStatus.EXIST)
                .user(productUser)
                .build();

        Product savedProduct = productRepository.save(product);

        return ProductRegistrationResponseDto.fromEntity(savedProduct);
    }

    public ProductInfoResponseDto getProduct(Long id) {

        Product product = productRepository.findById(id).orElseThrow(IllegalAccessError::new);

        return ProductInfoResponseDto.fromEntity(product);
    }

    @Transactional
    public ProductEditResponseDto updateProduct(Long id, ProductEditRequestDto productEditRequestDto) {

        Product product = productRepository.findById(id).orElseThrow(IllegalAccessError::new);

        product.update(productEditRequestDto);

        return ProductEditResponseDto.fromEntity(product);
    }
}
