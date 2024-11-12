package com.i3.delivery.store.controller;

import com.i3.delivery.store.dto.*;
import com.i3.delivery.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public StoreRegistrationResponseDto createStore(@RequestBody StoreRegistrationRequestDto storeRegistrationRequestDto) {

        return storeService.createStore(storeRegistrationRequestDto);
    }

    @GetMapping
    public List<StoreInfoResponseDto> getAllStore(){

        return storeService.getStores();
    }

    @GetMapping("/{storeId}")
    public StoreInfoResponseDto getStore(@PathVariable Long storeId) {

        return storeService.getStore(storeId);
    }

    @PatchMapping("/{storeId}")
    public StoreEditResponseDto updateStore(@PathVariable Long storeId, @RequestBody StoreEditRequsetDto storeEditRequsetDto) {

        return storeService.updateStore(storeId, storeEditRequsetDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStore(@PathVariable Long id) {

        return storeService.deleteStore(id);
    }
}
