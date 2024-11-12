package com.i3.delivery.store.service;

import com.i3.delivery.store.dto.StoreRegistrationRequestDto;
import com.i3.delivery.store.dto.StoreRegistrationResponseDto;
import com.i3.delivery.store.entity.Store;
import com.i3.delivery.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
