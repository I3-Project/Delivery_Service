package com.i3.delivery.domain.store.service;

import com.i3.delivery.domain.store.dto.*;
import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.store.enums.StoreStatus;
import com.i3.delivery.domain.store.repository.StoreRepository;
import com.i3.delivery.domain.store.repository.custom.impl.StoreRepositoryImpl;
import com.i3.delivery.domain.user.entity.User;
import com.i3.delivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreRepositoryImpl storeRepositoryImpl;
    private final UserRepository userRepository;

    public StoreRegistrationResponseDto createStore(User user, StoreRegistrationRequestDto storeRegistrationRequestDto) {

        User storeUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));

        Store store = Store.builder()
                .user(storeUser)
                .name(storeRegistrationRequestDto.getName())
                .description(storeRegistrationRequestDto.getDescription())
                .category(storeRegistrationRequestDto.getCategory())
                .phoneNumber(storeRegistrationRequestDto.getPhoneNumber())
                .address(storeRegistrationRequestDto.getAddress())
                .status(StoreStatus.CLOSE)
                .totalReviews(0)
                .ratingAvg(0)
                .build();

        storeRepository.save(store);

        return StoreRegistrationResponseDto.fromEntity(store);
    }

    public List<StoreInfoResponseDto> getStores() {

        return storeRepository.findAll().stream().map(store -> new StoreInfoResponseDto()).toList();
    }

    public StoreInfoResponseDto getStore(Long id) {

        Store store = storeRepository.findById(id).orElse(null);

        return StoreInfoResponseDto.fromEntity(store);
    }

    public List<StoreInfoResponseDto> getStoresByKeyword(String keyword) {

        return storeRepositoryImpl.findAll(keyword).stream().map(store -> new StoreInfoResponseDto()).toList();
    }

    @Transactional
    public StoreEditResponseDto updateStore(Long id, StoreEditRequsetDto storeEditRequsetDto) {

        Store store = storeRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        store.update(storeEditRequsetDto);

        return StoreEditResponseDto.from(store);
    }

    @Transactional
    public ResponseEntity<String> deleteStore(Long id) {

        Store store = storeRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        store.delete();

        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");

    }

    // TODO 여기서 transanctional 이 필요할까요?? 만약 쓴다면 readOnly = true 로 설정해주세요.
    @Transactional
    public List<StoreReviewResponseDto> getStoreAvgAndReviews(String name) {

        return storeRepositoryImpl.findStoreAvgAndReviews(name);
    }
}
