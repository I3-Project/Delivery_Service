package com.i3.delivery.domain.store.dto;

import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.store.enums.StoreStatus;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StoreRegistrationResponseDto {

    private String name;
    private String description;
    private Long categoryId;
    private long ownerId;
    private String address;
    private String phoneNumber;
    private StoreStatus status;
    private int totalReviews;
    private int ratingAvg;

    public static StoreRegistrationResponseDto fromEntity(Store store) {
        return StoreRegistrationResponseDto.builder()
                .name(store.getName())
                .description(store.getDescription())
                .categoryId(store.getCategory().getId())
                .ownerId(store.getUser().getId())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .status(store.getStatus())
                .totalReviews(store.getTotalReviews())
                .ratingAvg(store.getRatingAvg())
                .build();
    }
}
