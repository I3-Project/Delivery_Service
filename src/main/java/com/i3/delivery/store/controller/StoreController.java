package com.i3.delivery.store.controller;

import com.i3.delivery.store.dto.StoreRegistrationRequestDto;
import com.i3.delivery.store.dto.StoreRegistrationResponseDto;
import com.i3.delivery.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public StoreRegistrationResponseDto createStore(@RequestBody StoreRegistrationRequestDto storeRegistrationRequestDto) {
        return storeService.createStore(storeRegistrationRequestDto);
    }
//
//    @GetMapping
//    public ResponseBody getStore(){
//
//    }
//
//    @GetMapping
//    public ResponseBody getAllStore(){
//
//    }
//
//    @PatchMapping
//    public ResponseBody updateStore(){
//
//    }
//
//    @DeleteMapping
//    public ResponseBody deleteStore(){
//
//    }
}
