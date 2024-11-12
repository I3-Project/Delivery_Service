package com.i3.delivery.domain.store.dto;

import com.i3.delivery.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
    private Timestamp createdAt;
    private String createdBy;
    private Timestamp updatedAt;
    private String updatedBy;
    private Timestamp deletedAt;
    private String deletedBy;

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
        this.createdAt = store.getCreatedAt();
        this.createdBy = store.getCreatedBy();
        this.updatedAt = store.getUpdatedAt();
        this.updatedBy = store.getUpdatedBy();
        //this.deletedAt = store.getDeletedAt();
        this.deletedBy = store.getDeletedBy();
    }
}