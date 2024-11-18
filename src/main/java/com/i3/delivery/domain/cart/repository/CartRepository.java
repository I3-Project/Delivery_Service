package com.i3.delivery.domain.cart.repository;

import com.i3.delivery.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
