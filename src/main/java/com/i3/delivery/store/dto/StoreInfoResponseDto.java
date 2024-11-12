package com.i3.delivery.store.dto;

import com.i3.delivery.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreInfoResponseDto {

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

    public StoreInfoResponseDto(Store store) {
        this.name = store.getName();
        this.description = store.getDescription();
        this.category = store.getCategory();
        this.ownerId = store.getOwnerId();
        this.address = store.getAddress();
        this.phoneNumber = store.getPhoneNumber();
        this.status = store.getStatus();
        this.totalReviews = store.getTotalReviews();
        this.ratingAvg = store.getRatingAvg();
        this.createdBy = store.getCreatedBy();
    }
}