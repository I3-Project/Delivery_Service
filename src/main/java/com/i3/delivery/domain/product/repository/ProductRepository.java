package com.i3.delivery.domain.product.repository;

import com.i3.delivery.domain.order.entity.Order;
import com.i3.delivery.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
