package com.i3.delivery.domain.store.service;

import com.i3.delivery.domain.category.entity.Category;
import com.i3.delivery.domain.category.service.CategoryService;
import com.i3.delivery.domain.store.dto.*;
import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.store.enums.StoreStatus;
import com.i3.delivery.domain.store.repository.StoreRepository;
import com.i3.delivery.domain.store.repository.custom.impl.StoreRepositoryImpl;
import com.i3.delivery.domain.user.entity.User;
import com.i3.delivery.domain.user.entity.UserRoleEnum;
import com.i3.delivery.domain.user.repository.UserRepository;
import com.i3.delivery.global.exception.store.StoreDeletedException;
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
import java.util.Objects;
import java.util.UUID;
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
                .uuid(UUID.randomUUID())
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

    public Page<StoreInfoResponseDto> getStores(User user,Pageable pageable, int size) {

        if(user.getRole().equals(UserRoleEnum.MANAGER) || user.getRole().equals(UserRoleEnum.MASTER)){
            return storeRepositoryImpl.findStoresMaster(user,pageable,size);
        }
        return storeRepositoryImpl.findStores(user,pageable,size);
    }

    public StoreInfoResponseDto getStore(Long id, User user) {

        Store store = storeRepository.findById(id).orElse(null);

        if(Objects.requireNonNull(store).getDeletedAt() != null){
            if(user.getRole().equals(UserRoleEnum.MASTER) || user.getRole().equals(UserRoleEnum.MANAGER)){
                return StoreInfoResponseDto.fromEntity(Objects.requireNonNull(store));
            }else{
                throw new StoreDeletedException();
            }
        }

        return StoreInfoResponseDto.fromEntity(Objects.requireNonNull(store));
    }

    public List<StoreInfoResponseDto> getStoresByKeyword(String keyword,Pageable pageable, int size) {

        pageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());

        return storeRepositoryImpl.findAll(keyword, pageable)
                .stream().map(StoreInfoResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    public StoreEditResponseDto updateStore(Long id, StoreEditRequsetDto storeEditRequsetDto) {

        Store store = storeRepository.findById(id).orElseThrow(StoreNotFoundException::new);

        Category category = categoryService.findCategory(storeEditRequsetDto.getCategoryId());

        store.update(storeEditRequsetDto,category);

        return StoreEditResponseDto.from(store);
    }

    @Transactional
    public ResponseEntity<String> deleteStore(Long id) {

        Store store = storeRepository.findById(id).orElseThrow(StoreNotFoundException::new);

        store.delete();

        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");

    }

    @Transactional
    public StoreReviewResponsePage<StoreReviewResponseDto> getStoreReviewAvgAndReviews(String name,Pageable pageable,int size) {

        return storeRepositoryImpl.findStoreReviewAvgAndReviews(name,pageable,size);
    }

    public Store findStore(Long storeId) {

        return storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
    }

    @Transactional
    public StoreEditResponseDto updateStoreStatus(Long id, String status) {

        Store store = storeRepository.findById(id).orElseThrow(StoreNotFoundException::new);

        StoreStatus storeStatus = StoreStatus.valueOf(status);
        store.update(storeStatus);

        return StoreEditResponseDto.from(store);
    }
}
