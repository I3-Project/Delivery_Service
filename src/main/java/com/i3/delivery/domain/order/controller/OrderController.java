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
// TODO order -> orders   
@RequestMapping("/api/order")
// TODO @Validated 이걸 써야 동작하는 지 테스트
public class OrderController {

    // TODO final 을 써야 하는 이유 공부: lombok
    private final OrderService orderService;

    /* 1. 주문 등록 */
    // TODO hasRole 대신 hasAnyAuthoriy('MANAGER', 'OWNER', 'CUSTOMER')
    /*@PreAuthorize("hasAnyAuthority('MANAGER', 'OWNER', 'CUSTOMER')")
    @PostMapping
    public OrderResponseDto createOrder(
        // TODO @Valid
            @Valid @RequestBody OrderRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return orderService.createOrder(request, userDetails.getUser());
    }*/

    /* 2. 주문 취소*/
    @PreAuthorize("hasAnyAuthority('MANAGER', 'OWNER', 'CUSTOMER')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable("orderId") Long orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    // 3. 주문 내역 전체 조회
    @PreAuthorize("hasAnyAuthority('MANAGER', 'MASTER')")
    @GetMapping
    public ResponseEntity<Page<OrderListResponseDto>> getOrderList(
            @PageableDefault(page = 0, size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam Integer size
    ) {
        Page<OrderListResponseDto> responseDto = orderService.getOrderList(pageable, size);
        return ResponseEntity.ok(responseDto);
    }

    /* 4. 주문 내역 조회 (OWNER) */
    @PreAuthorize("hasAnyAuthority('MANAGER', 'OWNER')")
    @GetMapping("orders/{storeId}")
    public ResponseEntity<Page<OrderListResponseDto>> getStoreOrderList(
            @PageableDefault(page = 0, size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam Integer size,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Long storeId
    ) {
        Page<OrderListResponseDto> responseDto = orderService.getStoreOrderList(pageable, size, storeId, userDetails.getUser().getId());
        return ResponseEntity.ok(responseDto);
    }

}
