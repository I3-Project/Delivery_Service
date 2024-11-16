package com.i3.delivery.domain.review.repository;

import com.i3.delivery.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
