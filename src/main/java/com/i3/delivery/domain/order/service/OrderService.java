package com.i3.delivery.domain.order.service;

import com.i3.delivery.domain.order.dto.request.OrderRequestDto;
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
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    public OrderResponseDto createOrder(OrderRequestDto requestDto, User user) {
        // TODO 검증 부분은 클래스나 메소드로 추출
        
        // TODO 의미가 부여된 exception class 를 사용하는 것이 좋음
        // TODO 공통화된 응답을 위해서 Exception 도 공통화 해야함 -> 이후 globalExceptionHandler와 연결지어야함
        User orderUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));

        Store store = storeRepository.findById(requestDto.getStoreId())
                .orElseThrow(() -> new RuntimeException("가게 정보가 없습니다."));

        if (store.getStatus() == StoreStatus.CLOSE) {
            throw new IllegalArgumentException("가게 오픈 전입니다.");
        }

        // TODO 기본 생성자 보다는 builder, factory method 것 사용 권장 (생성자: 실수로 인해서 같은 타입이 존재할 경우, 다른 값이 들어갈 수 있음)
        Order order = Order.builder()
                .user(orderUser)
                .store(store)
                .orderType(OrderTypeEnum.valueOf(requestDto.getOrderType()))
                .oRequest(requestDto.getORequest())
                .build();

        List<OrderProduct> orderProductList = requestDto.getProductList().stream()
                .map(productListDto -> {
                    Product productList = productRepository.findById(productListDto.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("등록된 음식 정보가 존재하지 않습니다."));
                    return new OrderProduct(order, productList, productListDto.getQuantity());
                })
                .collect(Collectors.toList());


        // TODO private 메소드로 this.calculateTotalPrice();
        BigDecimal totalPrice = orderProductList.stream()
                .map(OrderProduct::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        order.setTotalPrice(totalPrice);
        order.setOrderProductList(orderProductList);

        Order savedOrder = orderRepository.save(order);

        // TODO 생성자 보다는 factory method
        return new OrderResponseDto(savedOrder);

    }

    @Transactional
    public void cancelOrder(Long orderId) {
        
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 정보가 존재하지 않습니다."));

        // TODO private method
        /* 주문이 생성된 후 5분 이내인지 확인*/
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(order.getCreatedAt(), currentTime);

        if (duration.getSeconds() > 300) {
            throw new IllegalStateException("주문 후 5분 이내에만 삭제할 수 있습니다.");
        }

        order.setOrderStatus(OrderStatusEnum.CANCELED);
    }
}
