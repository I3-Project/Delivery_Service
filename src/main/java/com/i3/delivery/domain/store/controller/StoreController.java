package com.i3.delivery.domain.store.controller;

import com.i3.delivery.domain.store.dto.*;
import com.i3.delivery.domain.store.service.StoreService;
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

    @GetMapping("/{id}")
    public StoreInfoResponseDto getStore(@PathVariable Long id) {

        return storeService.getStore(id);
    }

    @PatchMapping("/{id}")
    public StoreEditResponseDto updateStore(@PathVariable Long id, @RequestBody StoreEditRequsetDto storeEditRequsetDto) {

        return storeService.updateStore(id, storeEditRequsetDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStore(@PathVariable Long id) {

        return storeService.deleteStore(id);
    }
}
