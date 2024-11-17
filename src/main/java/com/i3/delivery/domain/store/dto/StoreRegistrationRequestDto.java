package com.i3.delivery.domain.store.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class StoreRegistrationRequestDto {

    private String name;
    private String description;
    private Long categoryId;
    private String address;
    private String phoneNumber;
}
