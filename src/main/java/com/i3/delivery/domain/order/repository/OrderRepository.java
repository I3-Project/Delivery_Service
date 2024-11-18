package com.i3.delivery.domain.order.repository;

import com.i3.delivery.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT p FROM Order p WHERE p.id = :id")
    Page<Order> findAllByOwnerId(Long id, Pageable pageable);

    //@Query("SELECT p FROM Order p WHERE p.id = :id")
    //Page<Order> findAllByUserId(Long userId, Pageable pageable);

    Page<Order> findAllByUser_Id(Long userId, Pageable pageable);
}
