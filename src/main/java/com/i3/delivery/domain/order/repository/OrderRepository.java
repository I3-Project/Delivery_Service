package com.i3.delivery.domain.order.repository;

import com.i3.delivery.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}