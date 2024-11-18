package com.i3.delivery.domain.store.repository.custom;

import com.i3.delivery.domain.store.dto.StoreInfoResponseDto;
import com.i3.delivery.domain.store.dto.StoreReviewResponseDto;
import com.i3.delivery.domain.store.dto.StoreReviewResponsePage;
import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreRepositoryCustom {
    List<Store> findAll(String keyword, Pageable pageable);
    StoreReviewResponsePage<StoreReviewResponseDto> findStoreReviewAvgAndReviews(String name, Pageable pageable, int size);
    Page<StoreInfoResponseDto> findStores(User user, Pageable pageable, int size);
    Page<StoreInfoResponseDto> findStoresMaster(User user, Pageable pageable, int size);
}
