package com.i3.delivery.store.dto;

import com.i3.delivery.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreEditResponseDto {

    private String name;
    private String description;
    private String category;
    private String address;
    private String phoneNumber;
    private String status;

    public StoreEditResponseDto(Store store) {
        this.name = store.getName();
        this.description = store.getDescription();
        this.category = store.getCategory();
        this.address = store.getAddress();
        this.phoneNumber = store.getPhoneNumber();
        this.status = store.getStatus();
    }
}
