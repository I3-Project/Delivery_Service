package com.i3.delivery.domain.product.service;

import com.i3.delivery.domain.product.Ai.GeminiRequest;
import com.i3.delivery.domain.product.dto.*;
import com.i3.delivery.domain.product.entity.Product;
import com.i3.delivery.domain.product.enums.ProductStatus;
import com.i3.delivery.domain.product.repository.ProductRepository;
import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.store.service.StoreService;
import com.i3.delivery.domain.user.entity.User;
import com.i3.delivery.domain.user.repository.UserRepository;
import com.i3.delivery.global.exception.product.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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

        String productName;
        String productDescription;
        GeminiRequest geminiRequest = new GeminiRequest();

        if(productRegistrationRequestDto.getName() == null){
            productName = geminiRequest.answer("음식점에서 파는 음식 명을 한글 10자 이내로 한개만 만들어 주세요.\n");
            productDescription = geminiRequest.answer(productName+"을 한글 20자 내로 설명해 주세요.\n");
        }else{
            String name = productRegistrationRequestDto.getName();
            productName = name;
            productDescription = geminiRequest.answer(name+"을 한글 20자 내로 설명해 주세요.\n");
        }

        Product product = Product.builder()
                .name(productName)
                .uuid(UUID.randomUUID())
                .description(productDescription)
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

        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        return ProductInfoResponseDto.fromEntity(product);
    }

    public Page<ProductInfoResponseDto> getProductAll(Pageable pageable, int size) {

        pageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());

        return productRepository.findAll(pageable).
                map(ProductInfoResponseDto::fromEntity);
    }

    @Transactional
    public ProductEditResponseDto updateProduct(Long id, ProductEditRequestDto productEditRequestDto) {

        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        product.update(productEditRequestDto);

        return ProductEditResponseDto.fromEntity(product);
    }

    @Transactional
    public ResponseEntity<String> deleteProduct(Long id) {

        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        product.delete();

        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
    }

    @Transactional
    public ResponseEntity<String> deleteProductAll(Long id) {

        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        productRepository.delete(product);

        return ResponseEntity.status(HttpStatus.OK).body("전부 삭제 완료");
    }
}
