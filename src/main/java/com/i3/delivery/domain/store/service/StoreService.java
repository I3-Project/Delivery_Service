package com.i3.delivery.domain.store.service;

import com.i3.delivery.domain.category.entity.Category;
import com.i3.delivery.domain.category.service.CategoryService;
import com.i3.delivery.domain.store.dto.*;
import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.store.enums.StoreStatus;
import com.i3.delivery.domain.store.repository.StoreRepository;
import com.i3.delivery.domain.store.repository.custom.impl.StoreRepositoryImpl;
import com.i3.delivery.domain.user.entity.User;
import com.i3.delivery.domain.user.repository.UserRepository;
import com.i3.delivery.global.exception.store.StoreNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreRepositoryImpl storeRepositoryImpl;
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    public StoreRegistrationResponseDto createStore(User user, StoreRegistrationRequestDto storeRegistrationRequestDto) {

        User storeUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));

        Category category = categoryService.findCategory(storeRegistrationRequestDto.getCategoryId());

        Store store = Store.builder()
                .user(storeUser)
                .name(storeRegistrationRequestDto.getName())
                .description(storeRegistrationRequestDto.getDescription())
                .category(category)
                .phoneNumber(storeRegistrationRequestDto.getPhoneNumber())
                .address(storeRegistrationRequestDto.getAddress())
                .status(StoreStatus.CLOSE)
                .totalReviews(0)
                .ratingAvg(1)
                .build();

        storeRepository.save(store);

        return StoreRegistrationResponseDto.fromEntity(store);
    }

    public Page<StoreInfoResponseDto> getStores(Pageable pageable, int size) {

        pageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());

        return storeRepository.findAll(pageable).
                map(StoreInfoResponseDto::fromEntity);
    }

    public StoreInfoResponseDto getStore(Long id) {

        Store store = storeRepository.findById(id).orElse(null);

        return StoreInfoResponseDto.fromEntity(store);
    }

    @Transactional
    public List<StoreInfoResponseDto> getStoresByKeyword(String keyword,Pageable pageable, int size) {

        pageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());

        return storeRepositoryImpl.findAll(keyword, pageable)
                .stream().map(StoreInfoResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    public StoreEditResponseDto updateStore(Long id, StoreEditRequsetDto storeEditRequsetDto) {

        Store store = storeRepository.findById(id).orElseThrow(StoreNotFoundException::new);

        Category category = findCategory(storeEditRequsetDto);
        store.update(storeEditRequsetDto,category);

        return StoreEditResponseDto.from(store);
    }

    private Category findCategory(StoreEditRequsetDto storeEditRequsetDto) {

        return categoryService.findCategory(storeEditRequsetDto.getCategoryId());
    }

    @Transactional
    public ResponseEntity<String> deleteStore(Long id) {

        Store store = storeRepository.findById(id).orElseThrow(StoreNotFoundException::new);

        store.delete();

        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");

    }

    // TODO 여기서 transanctional 이 필요할까요?? 만약 쓴다면 readOnly = true 로 설정해주세요.
    @Transactional
    public Page<StoreReviewResponseDto> getStoreAvgAndReviews(String name,Pageable pageable,int size) {

        return storeRepositoryImpl.findStoreAvgAndReviews(name,pageable,size);
    }

    public Store findStore(Long storeId) {

        return storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
    }
}
