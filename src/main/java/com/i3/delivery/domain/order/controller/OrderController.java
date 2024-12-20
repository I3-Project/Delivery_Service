package com.i3.delivery.domain.order.controller;

import com.i3.delivery.domain.order.dto.request.OrderRequestDto;
import com.i3.delivery.domain.order.dto.response.OrderListResponseDto;
import com.i3.delivery.domain.order.dto.response.OrderResponseDto;
import com.i3.delivery.domain.order.service.OrderService;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    /* 1. 주문 등록 */
    @PostMapping("/orders")
    public OrderResponseDto createOrder(
            @Valid @RequestBody OrderRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return orderService.createOrder(request, userDetails.getUser().getId());
    }

    /* 2. 주문 취소 */
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_OWNER', 'ROLE_USER')")
    @PostMapping("/orders/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable("orderId") Long orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        orderService.cancelOrder(orderId, userDetails.getUser().getId());
        return ResponseEntity.noContent().build();
    }

    // 3. 주문 내역 전체 조회 (관리자)
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_USER')")
    @GetMapping("/orders")
    public ResponseEntity<Page<OrderListResponseDto>> getOrderList(
            @PageableDefault(page = 0, size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam Integer size
    ) {
        Page<OrderListResponseDto> responseDto = orderService.getOrderList(pageable, size);
        return ResponseEntity.ok(responseDto);
    }

    /* 4. 주문 내역 조회 (OWNER) */
    @PreAuthorize("hasAnyAuthority('ROLE_MASTER', 'ROLE_USER')")
    @GetMapping("/orders/store/{storeId}")
    public ResponseEntity<Page<OrderListResponseDto>> getStoreOrderList(
            @PageableDefault(page = 0, size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam Integer size,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("storeId") Long storeId
    ) {
        Page<OrderListResponseDto> responseDto = orderService.getStoreOrderList(pageable, size, storeId, userDetails.getUser().getId());
        return ResponseEntity.ok(responseDto);
    }

    /* 5. 주문 내역 조회 (USER) */
    @PreAuthorize("hasAnyAuthority('ROLE_MASTER', 'ROLE_USER')")
    @GetMapping("/orders/user/{userId}")
    public ResponseEntity<Page<OrderListResponseDto>> getUserOrderList(
            @PageableDefault(page = 0, size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam Integer size,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("userId") Long suserId
    ) {
        Page<OrderListResponseDto> responseDto = orderService.getUserOrderList(pageable, size, userDetails.getUser().getId(), suserId);
        return ResponseEntity.ok(responseDto);
    }

    /* 6. 주문 삭제 */
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_OWNER', 'ROLE_USER')")
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable("orderId") Long orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        orderService.deleteOrder(orderId, userDetails.getUser().getId());
        return ResponseEntity.noContent().build();
    }

    /* 7.주문 상세 조회 */
    @PreAuthorize("hasAnyAuthority('ROLE_MASTER', 'ROLE_OWNER', 'ROLE_USER')")
    @GetMapping("/orders/{orderId}/details")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId) {
        OrderResponseDto order = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(order);
    }

}
