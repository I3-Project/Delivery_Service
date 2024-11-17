package com.i3.delivery.domain.store.dto;

import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.store.enums.StoreStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreEditResponseDto {

    private String name;
    private String description;
    private Long categoryId;
    private String address;
    private String phoneNumber;
    private StoreStatus status;
    private int totalReview;
    private int ratingAvg;

    public static StoreEditResponseDto from(Store store) {
        StoreEditResponseDto dto = new StoreEditResponseDto();
        dto.name = store.getName();
        dto.description = store.getDescription();
        dto.categoryId = store.getCategory().getId();
        dto.address = store.getAddress();
        dto.phoneNumber = store.getPhoneNumber();
        dto.status = store.getStatus();
        dto.totalReview = store.getTotalReviews();
        dto.ratingAvg = store.getRatingAvg();
        return dto;
    }
}
