package com.i3.delivery.domain.store.service;

import com.i3.delivery.domain.store.dto.*;
import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.store.repository.StoreRepository;
import com.i3.delivery.domain.store.repository.custom.impl.StoreRepositoryImpl;
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

    public StoreRegistrationResponseDto createStore(StoreRegistrationRequestDto storeRegistrationRequestDto) {

        Store store = new Store(storeRegistrationRequestDto);

        Store saveStore = storeRepository.save(store);

        StoreRegistrationResponseDto storeRegistrationResponseDto = new StoreRegistrationResponseDto(saveStore);

        return storeRegistrationResponseDto;
    }

    public List<StoreInfoResponseDto> getStores() {

        return storeRepository.findAll().stream().map(StoreInfoResponseDto::new).toList();
    }

    public StoreInfoResponseDto getStore(Long id) {

        /*Store store = storeRepository.findById(id).orElse(null);

        return new StoreInfoResponseDto(store);*/

        return null;
    }

    public List<StoreInfoResponseDto> getStoresByKeyword(String keyword) {

        return storeRepositoryImpl.findAll(keyword).stream().map(StoreInfoResponseDto::new).toList();
    }

    @Transactional
    public StoreEditResponseDto updateStore(Long id, StoreEditRequsetDto storeEditRequsetDto) {

       /* Store store = storeRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        store.update(storeEditRequsetDto)

        return StoreEditResponseDto.from(store);;*/
        return null;
    }

    @Transactional
    public ResponseEntity<String> deleteStore(Long id) {

        /*Store store = storeRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        store.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");*/
        return null;

    }

    // TODO 여기서 transanctional 이 필요할까요?? 만약 쓴다면 readOnly = true 로 설정해주세요.
    @Transactional
    public List<StoreReviewResponseDto> getStoreAvgAndReviews(String name) {

        return storeRepositoryImpl.findStoreAvgAndReviews(name);
    }
}
