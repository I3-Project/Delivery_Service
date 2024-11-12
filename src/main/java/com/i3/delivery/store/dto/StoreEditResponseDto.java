package com.i3.delivery.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreEditResponseDto {

    private String name;
    private String description;
    private String category;
    private long ownerId;
    private String address;
    private String phoneNumber;
    private String status;
    private int totalReviews;
    private int ratingAvg;

    public StoreEditResponseDto(String name, String description, String category, long ownerId, String address, String phoneNumber, String status, int totalReviews, int ratingAvg) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.ownerId = ownerId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.totalReviews = totalReviews;
        this.ratingAvg = ratingAvg;
    }
}
