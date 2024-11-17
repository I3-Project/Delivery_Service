package com.i3.delivery.domain.review.entity;

import com.i3.delivery.domain.order.entity.Order;
import com.i3.delivery.domain.review.dto.ReviewRequestDto;
import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.user.entity.User;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import com.i3.delivery.global.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name="p_review")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column
    private UUID reviewUId;

    @Column(nullable = false)
    @Size(max = 100, message = "리뷰 내용은 100자까지 작성 가능합니다.")
    private String content;

    @Column(nullable = false)
    //@Size(min=1, max=5, message = "평점은 1-5점만 입력 가능합니다.")
    private Integer rating;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewStatusEnum reviewStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    private String deletedBy;
    @PreUpdate
    public void updateDeleteField(){
        if (reviewStatus == ReviewStatusEnum.DELETED) {
            this.deletedAt = LocalDateTime.now();
            this.deletedBy = getUserName();
        }
    }
    private static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetailsImpl) {
            return userDetailsImpl.getUser().getNickname();
        }
        return null;
    }

    /* 리뷰 등록 */
    @Builder
    public static Review createReview(ReviewRequestDto request, User user, Order order, Store store) {
        return Review.builder()
                .user(user)
                .order(order)
                .store(store)
                .content(request.getContent())
                .rating(request.getRating())
                .reviewStatus(ReviewStatusEnum.UPLOADED)
                .build();
    }

    /* 리뷰 수정 */
    public void updateReview(String content, Integer rating) {
        /*Review.builder()
                .content(content)
                .rating(rating)
                .reviewStatus(ReviewStatusEnum.FIXED)
                .build();*/
        this.content = content;
        this.rating = rating;
        this.reviewStatus = ReviewStatusEnum.FIXED;
    }
}
