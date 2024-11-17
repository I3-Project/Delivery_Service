package com.i3.delivery.domain.payment.service;

import com.i3.delivery.domain.order.entity.Order;
import com.i3.delivery.domain.order.entity.enums.OrderStatusEnum;
import com.i3.delivery.domain.order.repository.OrderRepository;
import com.i3.delivery.domain.payment.dto.PaymentRequestDto;
import com.i3.delivery.domain.payment.dto.PaymentResponseDto;
import com.i3.delivery.domain.payment.entity.Payment;
import com.i3.delivery.domain.payment.entity.enums.PaymentStatusEnum;
import com.i3.delivery.domain.payment.repository.PaymentRepository;
import com.i3.delivery.domain.review.entity.Review;
import com.i3.delivery.domain.review.entity.ReviewStatusEnum;
import com.i3.delivery.domain.user.entity.User;
import com.i3.delivery.domain.user.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public PaymentResponseDto createPayment(PaymentRequestDto request, Long userId) {

        Order order = orderRepository.findById(request.getOrderId())
              .orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));

        if (!order.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("회원님의 주문내역이 아닙니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보가 없습니다."));

        Payment payment = paymentRepository.save(Payment.builder()
                .order(order)
                .totalPrice(request.getTotalPrice())
                .pgId(request.getPgId())
                .paymentStatus(PaymentStatusEnum.PENDING)
                .user(user)
                .build());

        return PaymentResponseDto.toResponseDto(payment, userId);
    }

    @Transactional
    public void cancelPayment(Long paymentId, Long userId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보가 없습니다."));

        if (!payment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("회원님의 결제 내역이 아닙니다.");
        }

        payment.setPaymentStatus(PaymentStatusEnum.CANCELED);
    }
}
