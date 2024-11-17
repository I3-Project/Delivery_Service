package com.i3.delivery.domain.review.repository;

import com.i3.delivery.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByStore_Id(Long storeId, Pageable pageable);

    Page<Review> findAllByUser_Id(Long userId, Pageable pageable);
}
