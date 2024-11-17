package com.i3.delivery.domain.review.service;

import com.i3.delivery.domain.order.entity.Order;
import com.i3.delivery.domain.order.repository.OrderRepository;
import com.i3.delivery.domain.review.dto.ReviewRequestDto;
import com.i3.delivery.domain.review.dto.ReviewResponseDto;
import com.i3.delivery.domain.review.entity.Review;
import com.i3.delivery.domain.review.entity.ReviewStatusEnum;
import com.i3.delivery.domain.review.repository.ReviewRepository;
import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.store.repository.StoreRepository;
import com.i3.delivery.domain.user.entity.User;
import com.i3.delivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    public ReviewResponseDto createReview(ReviewRequestDto request, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("주문 정보가 없습니다."));

        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("가게 정보가 존재하지 않습니다."));

        Review review = Review.createReview(request, user, order, store);

        return ReviewResponseDto.toResponseDto(reviewRepository.save(review));
    }

    @Transactional
    public ReviewResponseDto updateReview(ReviewRequestDto request, Long reviewId, Long userId) {

        Review review = reviewRepository.findById(reviewId)
                .filter(p -> p.getReviewStatus() == ReviewStatusEnum.DELETED)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 리뷰를 찾을 수 없거나 삭제되었습니다."));

        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("회원님이 등록한 리뷰가 아닙니다.");
        }

        Review.updateReview(request.getContent(), request.getRating());

        return ReviewResponseDto.toResponseDto(review);
    }
}
