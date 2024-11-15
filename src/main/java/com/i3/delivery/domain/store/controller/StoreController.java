package com.i3.delivery.domain.store.controller;

import com.i3.delivery.domain.store.dto.*;
import com.i3.delivery.domain.store.service.StoreService;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PreAuthorize("hasAnyAuthority('MANAGER', 'MASTER')")
    @PostMapping
    public StoreRegistrationResponseDto createStore(@Validated @RequestBody StoreRegistrationRequestDto storeRegistrationRequestDto,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return storeService.createStore(userDetails.getUser(), storeRegistrationRequestDto);
    }

    @GetMapping
    public List<StoreInfoResponseDto> getAllStore(){

        return storeService.getStores();
    }

    @GetMapping("/{id}")
    public StoreInfoResponseDto getStore(@PathVariable(name = "id") Long id) {

        return storeService.getStore(id);
    }

    @GetMapping("/keyword/{keyword}")
    public List<StoreInfoResponseDto> getStoresByKeyword(@PathVariable(name = "keyword") String keyword) {

        return storeService.getStoresByKeyword(keyword);
    }

    @GetMapping("/store/{name}")
    public List<StoreReviewResponseDto> getStoreAvgAndReviews(@PathVariable(name = "name") String name) {

        return storeService.getStoreAvgAndReviews(name);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'OWNER', 'MASTER')")
    @PatchMapping("/{id}")
    public StoreEditResponseDto updateStore(@PathVariable(name = "id") Long id, @RequestBody StoreEditRequsetDto storeEditRequsetDto) {

        return storeService.updateStore(id, storeEditRequsetDto);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'OWNER', 'MASTER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStore(@PathVariable(name = "id") Long id) {

        return storeService.deleteStore(id);
    }
}
