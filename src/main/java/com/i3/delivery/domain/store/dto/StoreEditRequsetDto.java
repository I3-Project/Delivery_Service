package com.i3.delivery.domain.store.dto;

import com.i3.delivery.domain.store.enums.StoreStatus;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class StoreEditRequsetDto {

    private String name;
    private String description;
    private Long categoryId;
    private String address;
    private String phoneNumber;
    private StoreStatus status;
    private int totalReviews;
    private int ratingAvg;
}