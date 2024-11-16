package com.i3.delivery.domain.review.dto;

import com.i3.delivery.domain.review.entity.ReviewStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {
    private Long userId;
    private Long storeId;
    private Long orderId;
    private String content;
    private Integer rating;
    private ReviewStatusEnum reviewStatus;
}
