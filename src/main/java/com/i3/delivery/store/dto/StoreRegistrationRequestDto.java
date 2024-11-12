package com.i3.delivery.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreRegistrationRequestDto {

    private String name;
    private String description;
    private String category;
    private long ownerId;
    private String address;
    private String phoneNumber;
    private String status;
    private int totalReviews;
    private int ratingAvg;
    private String createdBy;
}
