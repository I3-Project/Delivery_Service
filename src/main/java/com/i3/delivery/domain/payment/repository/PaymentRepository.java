package com.i3.delivery.domain.payment.repository;

import com.i3.delivery.domain.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Page<Payment> findAllByUser_Id(Long userId, Pageable pageable);

    @Query("SELECT p FROM Payment p WHERE p.order.store.id = :storeId")
    Page<Payment> findAllByStore_Id(Long storeId, Pageable pageable);
}
