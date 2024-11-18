package com.i3.delivery.domain.cart.repository;

import com.i3.delivery.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUser_Id(Long userId);
}
