package com.i3.delivery.domain.review.entity;

import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.global.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Entity
@Table(name="p_review")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "review_id", updatable = false, nullable = false)
    private UUID reviewId;

    @Column(nullable = false)
    private UUID reviewUId;

    @Column(nullable = false)
    @Size(max = 100, message = "리뷰 내용은 100자까지 작성 가능합니다.")
    private String content;

    @Column(nullable = false)
    @Size(min=1, max=5, message = "평점은 1-5점만 입력 가능합니다.")
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

}
