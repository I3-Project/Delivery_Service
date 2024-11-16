package com.i3.delivery.domain.review.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewStatusEnum {
    UPLOADED("REVIEW_STATUS_UPLOADED"),
    DELETED("REVIEW_STATUS_DELETED");

    private final String status;
}
