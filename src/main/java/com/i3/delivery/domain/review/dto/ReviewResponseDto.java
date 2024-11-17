package com.i3.delivery.domain.review.dto;

import com.i3.delivery.domain.review.entity.Review;
import com.i3.delivery.domain.review.entity.ReviewStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {

    private Long userId;
    private Long storeId;
    private Long orderId;
    private String content;
    private Integer rating;
    private ReviewStatusEnum reviewStatus;

    public static ReviewResponseDto toResponseDto(Review review) {
        return new ReviewResponseDto(
                review.getUser().getId(),
                review.getStore().getId(),
                review.getOrder().getId(),
                review.getContent(),
                review.getRating(),
                review.getReviewStatus()
        );
    }
}
