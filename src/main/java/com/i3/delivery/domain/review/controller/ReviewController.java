package com.i3.delivery.domain.review.controller;

import com.i3.delivery.domain.review.dto.ReviewRequestDto;
import com.i3.delivery.domain.review.dto.ReviewResponseDto;
import com.i3.delivery.domain.review.service.ReviewService;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

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

    /* 2. 리뷰 수정 */
    @PreAuthorize("hasAnyAuthority('MANAGER', 'MASTER', 'USER')")
    @PatchMapping("/{review_id}")
    public ReviewResponseDto updateReview(@RequestBody ReviewRequestDto request,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @PathVariable Long reviewId) {

        return reviewService.updateReview(request, reviewId, userDetails.getUser().getId());
    }
}
