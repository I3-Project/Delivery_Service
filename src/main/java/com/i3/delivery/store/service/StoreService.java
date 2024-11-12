package com.i3.delivery.store.service;

import com.i3.delivery.store.dto.StoreRegistrationRequestDto;
import com.i3.delivery.store.dto.StoreRegistrationResponseDto;
import com.i3.delivery.store.entity.Store;
import com.i3.delivery.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
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


    public List<StoreRegistrationResponseDto> getStores() {
        
        return storeRepository.findAll().stream().map(StoreRegistrationResponseDto::new).toList();
    }
}
