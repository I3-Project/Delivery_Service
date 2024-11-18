package com.i3.delivery.domain.order.service;

import com.i3.delivery.domain.cart.entity.Cart;
import com.i3.delivery.domain.cart.repository.CartRepository;
import com.i3.delivery.domain.order.dto.request.OrderRequestDto;
import com.i3.delivery.domain.order.dto.response.OrderListResponseDto;
import com.i3.delivery.domain.order.dto.response.OrderResponseDto;
import com.i3.delivery.domain.order.entity.Order;
import com.i3.delivery.domain.order.entity.OrderProduct;
import com.i3.delivery.domain.order.entity.enums.OrderStatusEnum;
import com.i3.delivery.domain.order.entity.enums.OrderTypeEnum;
import com.i3.delivery.domain.order.repository.OrderRepository;
import com.i3.delivery.domain.product.entity.Product;
import com.i3.delivery.domain.product.repository.ProductRepository;
import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.store.enums.StoreStatus;
import com.i3.delivery.domain.store.repository.StoreRepository;
import com.i3.delivery.domain.user.entity.User;
import com.i3.delivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final CartRepository cartRepository;

    public OrderResponseDto createOrder(OrderRequestDto requestDto, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));

        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new RuntimeException("가게 정보가 없습니다."));

        if (store.getStatus() == StoreStatus.CLOSE) {
            throw new IllegalArgumentException("가게 오픈 전입니다.");
        }

        List<Cart> orderCarttList = cartRepository.findAllByUser_Id(userId);

        if (orderCarttList.isEmpty()) {
            throw new IllegalArgumentException("장바구니가 비었습니다.");
        }

        BigDecimal totalPrice = orderCarttList.stream()
                .map(cart -> BigDecimal.valueOf(cart.getQuantity())
                        .multiply(cart.getProduct().getPrice())) // 수량 * 상품 가격
                .reduce(BigDecimal.ZERO, BigDecimal::add); // 총합 계산

        Order order = Order.createOrder(user, store, requestDto.getOrderType(), requestDto.getOrderRequest(), totalPrice, user.getAddress());

        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDto(savedOrder);

    }

    /* 2. 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId, Long userId) {
        
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 정보가 존재하지 않습니다."));

        if (!order.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("회원님의 주문내역이 아닙니다.");
        }

        /* 주문이 생성된 후 5분 이내인지 확인*/
        cancelTimeCheck(order);

        order.setOrderStatus(OrderStatusEnum.CANCELED);
    }

    /* 3. 주문 목록 조회 */
    @Transactional(readOnly = true)
    public Page<OrderListResponseDto> getOrderList(Pageable pageable, Integer size) {

        pageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());

        return orderRepository.findAll(pageable).map(order -> new OrderListResponseDto (
                order.getUser().getId(),
                order.getId(),
                order.getProductName(),
                order.getTotalPrice(),
                order.getOrderStatus(),
                order.getOrderRequest(),
                order.getCreatedAt(),
                order.getCreatedBy()
        ));
    }


    @Transactional(readOnly = true)
    public Page<OrderListResponseDto> getStoreOrderList(Pageable pageable, Integer size, Long storeId, Long ownerId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("가게 등록 정보가 존재하지 않습니다.")
        );

        pageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());

        if (!store.getUser().getId().equals(ownerId)) {
            throw new IllegalArgumentException("회원님의 가게가 아닙니다.");
        }

        Page<Order> ownerOrderList = orderRepository.findAllByOwnerId(storeId, pageable);

        List<OrderListResponseDto> responseDtoList = ownerOrderList.stream()
                .map(OrderListResponseDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(responseDtoList, pageable, ownerOrderList.getTotalElements());

    }

    public Page<OrderListResponseDto> getUserOrderList(Pageable pageable, Integer size, Long userId, Long suserId) {

        if (!userId.equals(suserId)) {
            throw new IllegalArgumentException("회원님이 주문한 주문 내역이 아닙니다.");
        }

        pageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());

        Page<Order> userOrderList = orderRepository.findAllByUserId(userId, pageable);

        List<OrderListResponseDto> responseDtoList = userOrderList.stream()
                .map(OrderListResponseDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(responseDtoList, pageable, userOrderList.getTotalElements());
    }

    @Transactional
    public void deleteOrder(Long orderId, Long userId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 정보가 존재하지 않습니다."));

        if (!order.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("회원님의 주문내역이 아닙니다.");
        }

        order.setOrderStatus(OrderStatusEnum.DELETED);
    }

    public OrderResponseDto getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));
        return new OrderResponseDto(order);
    }

    private void cancelTimeCheck(Order order) {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(order.getCreatedAt(), currentTime);

        if (duration.getSeconds() > 300) {
            throw new IllegalStateException("주문 후 5분 이내에만 삭제할 수 있습니다.");
        }
    }
}
