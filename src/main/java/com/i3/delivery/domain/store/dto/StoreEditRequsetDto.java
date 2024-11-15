package com.i3.delivery.domain.store.dto;

import com.i3.delivery.domain.store.enums.StoreStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreEditRequsetDto {

    private String name;
    private String description;
    private String category;
    private String address;
    private String phoneNumber;
    private StoreStatus status;
    private int totalReviews;
    private int ratingAvg;
}