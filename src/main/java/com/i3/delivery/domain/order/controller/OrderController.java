package com.i3.delivery.domain.order.controller;

import com.i3.delivery.domain.order.dto.request.OrderRequestDto;
import com.i3.delivery.domain.order.dto.response.OrderResponseDto;
import com.i3.delivery.domain.order.entity.Order;
import com.i3.delivery.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")

public class OrderController {

    private final OrderService orderService;

    /* 1. 주문 등록 */
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_OWNER') or hasRole('ROLE_CUSTOMER')")
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @Validated @RequestBody OrderRequestDto request/*,
            @AuthenticationPrincipal UserDetailsImpl userDetails*/) {

        OrderResponseDto createOrder = orderService.createOrder(request/*, userDetails.getUser()*/);

        return ResponseEntity.status(HttpStatus.CREATED).body(createOrder);
    }
}
