package com.i3.delivery.domain.review.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewStatusEnum {
    UPLOADED("REVIEW_STATUS_UPLOADED"),
    FIXED("REVIEW_STATUS_FIXED"),
    DELETED("REVIEW_STATUS_DELETED");

    private final String status;
}
