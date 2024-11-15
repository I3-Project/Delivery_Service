package com.i3.delivery.domain.store.dto;

import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.store.enums.Status;
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
    private Status status;

    public static StoreEditResponseDto from(Store store) {
        StoreEditResponseDto dto = new StoreEditResponseDto();
        dto.name = store.getName();
        dto.description = store.getDescription();
        dto.category = store.getCategory();
        dto.address = store.getAddress();
        dto.phoneNumber = store.getPhoneNumber();
        dto.status = store.getStatus();
        return dto;
    }
}
