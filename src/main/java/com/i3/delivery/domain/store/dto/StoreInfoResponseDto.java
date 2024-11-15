package com.i3.delivery.domain.store.dto;

import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.store.enums.StoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreInfoResponseDto {

    private String name;
    private String description;
    private Long categoryId;
    private long ownerId;
    private String address;
    private String phoneNumber;
    private StoreStatus status;
    private int totalReviews;
    private int ratingAvg;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private LocalDateTime deletedAt;
    private String deletedBy;

    public static StoreInfoResponseDto fromEntity(Store store) {
        return StoreInfoResponseDto.builder()
                .name(store.getName())
                .description(store.getDescription())
                .categoryId(store.getCategory().getId())
                .ownerId(store.getUser().getId())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .status(store.getStatus())
                .totalReviews(store.getTotalReviews())
                .ratingAvg(store.getRatingAvg())
                .createdAt(store.getCreatedAt())
                .createdBy(store.getCreatedBy())
                .updatedAt(store.getUpdatedAt())
                .updatedBy(store.getUpdatedBy())
                .deletedAt(store.getDeletedAt())
                .deletedBy(store.getDeletedBy())
                .build();
    }
}