package com.i3.delivery.domain.store.controller;

import com.i3.delivery.domain.store.dto.*;
import com.i3.delivery.domain.store.service.StoreService;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @PreAuthorize("hasAnyAuthority('ROLE_MASTER', 'ROLE_MANAGER')")
    @PostMapping
    public StoreRegistrationResponseDto createStore(@Validated @RequestBody StoreRegistrationRequestDto storeRegistrationRequestDto,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return storeService.createStore(userDetails.getUser(), storeRegistrationRequestDto);
    }

    @GetMapping
    public Page<StoreInfoResponseDto> getAllStore(@PageableDefault(page = 0, size = 10,
            sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                  @RequestParam Integer size){

        return storeService.getStores(pageable,size);
    }

    @GetMapping("/{id}")
    public StoreInfoResponseDto getStore(@PathVariable(name = "id") Long id) {

        return storeService.getStore(id);
    }

    @GetMapping("/keyword/{keyword}")
    public List<StoreInfoResponseDto> getStoresByKeyword(@PathVariable(name = "keyword") String keyword,
                                                         @PageableDefault(page = 0, size = 10, sort = "createdAt",
                                                                 direction = Sort.Direction.DESC) Pageable pageable,
                                                         @RequestParam Integer size) {

        return storeService.getStoresByKeyword(keyword,pageable,size);
    }

    @GetMapping("/store/{name}")
    public List<StoreReviewResponseDto> getStoreAvgAndReviews(@PathVariable(name = "name") String name) {

        return storeService.getStoreAvgAndReviews(name);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_OWNER', 'ROLE_MASTER')")
    @PatchMapping("/{id}")
    public StoreEditResponseDto updateStore(@PathVariable(name = "id") Long id, @RequestBody StoreEditRequsetDto storeEditRequsetDto) {

        return storeService.updateStore(id, storeEditRequsetDto);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_OWNER', 'ROLE_MASTER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStore(@PathVariable(name = "id") Long id) {

        return storeService.deleteStore(id);
    }
}
