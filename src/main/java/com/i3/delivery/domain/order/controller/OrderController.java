package com.i3.delivery.domain.order.controller;

import com.i3.delivery.domain.order.dto.request.OrderRequestDto;
import com.i3.delivery.domain.order.dto.response.OrderResponseDto;
import com.i3.delivery.domain.order.entity.Order;
import com.i3.delivery.domain.order.service.OrderService;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")

public class OrderController {

    private final OrderService orderService;

    /* 1. 주문 등록 */
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_OWNER') or hasRole('ROLE_CUSTOMER')")
    @PostMapping
    public OrderResponseDto createOrder(
            @Validated @RequestBody OrderRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return orderService.createOrder(request, userDetails.getUser());
    }

    /* 2. 주문 취소*/
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_OWNER') or hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancleOrder(@PathVariable("orderId") UUID orderId) {
        orderService.cancleOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
