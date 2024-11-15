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
// TODO order -> orders   
@RequestMapping("/api/order")
// TODO @Validated 이걸 써야 동작하는 지 테스트
public class OrderController {

    // TODO final 을 써야 하는 이유 공부: lombok
    private final OrderService orderService;

    /* 1. 주문 등록 */
    // TODO hasRole 대신 hasAnyAuthoriy('MANAGER', 'OWNER', 'CUSTOMER')
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_OWNER') or hasRole('ROLE_CUSTOMER')")
    @PostMapping
    public OrderResponseDto createOrder(
        // TODO @Valid
            @Validated @RequestBody OrderRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return orderService.createOrder(request, userDetails.getUser());
    }

    /* 2. 주문 취소*/
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_OWNER') or hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
