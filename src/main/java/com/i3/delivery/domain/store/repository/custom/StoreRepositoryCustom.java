package com.i3.delivery.domain.store.repository.custom;

import com.i3.delivery.domain.store.dto.StoreReviewResponseDto;
import com.i3.delivery.domain.store.entity.Store;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreRepositoryCustom {
    List<Store> findAll(String keyword, Pageable pageable);
    List<StoreReviewResponseDto> findStoreAvgAndReviews(String name);
}
