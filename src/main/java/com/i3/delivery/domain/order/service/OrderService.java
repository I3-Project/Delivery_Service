package com.i3.delivery.domain.order.service;

import com.i3.delivery.domain.order.dto.request.OrderRequestDto;
import com.i3.delivery.domain.order.dto.response.OrderResponseDto;
import com.i3.delivery.domain.order.entity.Order;
import com.i3.delivery.domain.order.entity.OrderProduct;
import com.i3.delivery.domain.order.entity.enums.OrderStatusEnum;
import com.i3.delivery.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    public OrderResponseDto createOrder(OrderRequestDto requestDto/*,User user*/) {

        /*User orderUser = userRepository.findById(user.getUser_id())
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));

        Address address = addressRepository.findById(requestDto.getAddressId())
                .orElseThrow(() -> new RuntimeException("주소를 찾을 수 없습니다."));

        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new RuntimeException("가게 정보가 없습니다."));

        if (!restaurant.getIsOpened()) {
            throw new IllegalArgumentException("가게 오픈 전입니다.");
        } */
        Order order = new Order(user, store, requestDto.getType());

        List<OrderProduct> orderProductList = requestDto.getProductList().stream()
                .map(productListDto -> {
                    Product productList = ProductRepository.findById(productListDto.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("등록된 음식 정보가 존재하지 않습니다."));
                    return new OrderProduct(order, productList, productListDto.getQuantity());
                })
                .collect(Collectors.toList());


        BigDecimal totalPrice = orderProductList.stream()
                .map(OrderProduct::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        order.setTotalPrice(totalPrice);
        order.setOrderProductList(orderProductList);

        Order savedOrder = orderRepository.save(order);

        return OrderResponseDto.reResponseDto(savedOrder);
    }

    @Transactional
    public void cancleOrder(UUID orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 정보가 존재하지 않습니다."));

        /* 주문이 생성된 후 5분 이내인지 확인*/
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(order.getCreatedAt(), currentTime);

        if (duration.getSeconds() > 300) {
            throw new IllegalStateException("주문 후 5분 이내에만 삭제할 수 있습니다.");
        }

        order.setOrderStatus(OrderStatusEnum.valueOf("CANCELED"));

        orderRepository.save(order);
    }
}
