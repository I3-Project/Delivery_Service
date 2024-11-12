package com.i3.delivery.store.dto;

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
    private String status;
}