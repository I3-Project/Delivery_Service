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

    // 가게 주인, 관리자 권한을 가진 사용자만 가게를 추가할 수 있도록 제한해야함
    @PostMapping
    public StoreRegistrationResponseDto createStore(@RequestBody StoreRegistrationRequestDto storeRegistrationRequestDto) {

        return storeService.createStore(storeRegistrationRequestDto);
    }

    // 가게 이름, 카테고리 등 검색 및 정렬 기능(지역별 필터링 고려)
    // 평점 및 리뷰 조회 : 가게에 대한 평균 평점 계산과 리뷰 리스트를 조회
    //N+1 문제 해결 : 가게 목록을 조회할 때 평점을 함께 조회할 수 있도록 성능 최적화(QueryDSL 등 활용)
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
