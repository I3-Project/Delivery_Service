package com.i3.delivery.domain.store.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class StoreReviewMeta {
    private int totalReviews;
    private int averageRating;

    public StoreReviewMeta(int totalReviews, int averageRating) {
        this.totalReviews = totalReviews;
        this.averageRating = averageRating;
    }
}