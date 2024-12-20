package com.i3.delivery.domain.store.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreStatus {

    OPEN("OPEN"),
    CLOSE("CLOSE"),
    DAYOFF("DAYOFF"),
    DELETED("DELETED");

    private final String flag;
}
