package com.i3.delivery.domain.review.controller;

import com.i3.delivery.domain.review.dto.ReviewRequestDto;
import com.i3.delivery.domain.review.dto.ReviewResponseDto;
import com.i3.delivery.domain.review.service.ReviewService;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /* 1. 리뷰 등록 */
    @PreAuthorize("hasAnyAuthority('MANAGER', 'MASTER', 'USER')")
    @PostMapping("/reviews")
    public ReviewResponseDto createReview(@RequestBody ReviewRequestDto request,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reviewService.createReview(request, userDetails.getUser().getId());
    }
}
