package com.i3.delivery.domain.payment.repository;

import com.i3.delivery.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
