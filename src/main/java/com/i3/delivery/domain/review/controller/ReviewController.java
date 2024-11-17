package com.i3.delivery.domain.review.controller;

import com.i3.delivery.domain.review.dto.ReviewRequestDto;
import com.i3.delivery.domain.review.dto.ReviewResponseDto;
import com.i3.delivery.domain.review.service.ReviewService;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /* 1. 리뷰 등록 */
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_MASTER', 'ROLE_USER')")
    @PostMapping("/reviews")
    public ReviewResponseDto createReview(@RequestBody ReviewRequestDto request,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reviewService.createReview(request, userDetails.getUser().getId());
    }

    /* 2. 리뷰 수정 */
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_MASTER', 'ROLE_USER')")
    @PatchMapping("/reviews/{reviewId}")
    public ReviewResponseDto updateReview(@RequestBody ReviewRequestDto request,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @PathVariable Long reviewId) {

        return reviewService.updateReview(request, reviewId, userDetails.getUser().getId());
    }

    /* 3. 리뷰 삭제 */
    @DeleteMapping("reviews/{reviewId}")
    @PreAuthorize("hasAnyAuthority('ROLE_MASTER', 'ROLE_USER')")
    public ResponseEntity<Void> deleteProduct(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @PathVariable Long reviewId) {

        reviewService.deleteReview(reviewId, userDetails.getUser().getId());
        return ResponseEntity.noContent().build();

    }

    /* 4. 리뷰 조회 (OWNER) */
    @GetMapping("reviews/{storeId}")
    public Page<ReviewResponseDto> getStoreReviews(
            @PageableDefault(page = 0, size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam Integer size,
            @PathVariable("storeId") Long storeId
    ) {
        return reviewService.getStoreReviews(pageable, size, storeId);
    }

    /* 5. 리뷰 조회 (USER) */
    @GetMapping("reviews/myReviews")
    @PreAuthorize("hasAnyAuthority('ROLE_MASTER', 'ROLE_USER')")
    public Page<ReviewResponseDto> getUserReviews(
            @PageableDefault(page = 0, size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam Integer size,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return reviewService.getUserReviews(pageable, size, userDetails.getUser().getId());
    }

}
