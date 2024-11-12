package com.i3.delivery.store.service;

import com.i3.delivery.store.dto.*;
import com.i3.delivery.store.entity.Store;
import com.i3.delivery.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreRegistrationResponseDto createStore(StoreRegistrationRequestDto storeRegistrationRequestDto) {

        Store store = new Store(storeRegistrationRequestDto);

        Store saveStore = storeRepository.save(store);

        StoreRegistrationResponseDto storeRegistrationResponseDto = new StoreRegistrationResponseDto(saveStore);

        return storeRegistrationResponseDto;
    }


    public List<StoreInfoResponseDto> getStores() {

        return storeRepository.findAll().stream().map(StoreInfoResponseDto::new).toList();
    }

    public StoreInfoResponseDto getStore(Long storeId) {

        Store store = storeRepository.findById(storeId).orElse(null);

        return new StoreInfoResponseDto(store);
    }

    public StoreEditResponseDto updateStore(Long storeId, StoreEditRequsetDto storeEditRequsetDto) {

        Store store = storeRepository.findById(storeId).orElse(null);

        store.update(storeEditRequsetDto);

        return new StoreEditResponseDto(store);
    }

    public ResponseEntity<String> deleteStore(Long id) {

        storeRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
    }
}
