package com.i3.delivery.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreReviewResponseDto {

    private int ratingAvg;
    private Long id;
    private Long storeId;
    private int rating;
    private String reviewText;
    private String userName;
    private LocalDateTime createdAt;
}
