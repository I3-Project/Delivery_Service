package com.i3.delivery.domain.payment.dto;

import com.i3.delivery.domain.payment.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponseDto {
    private Long paymentId;
    private Long userId;
    private String pgId;
    private BigDecimal totalPrice;

    public PaymentResponseDto(Payment payment) {
        this.paymentId = payment.getId();
        this.userId = payment.getUser().getId();
        this.pgId = payment.getPgId();
        this.totalPrice = payment.getTotalPrice();
    }

    public static PaymentResponseDto toResponseDto(Payment payment, Long userId) {
        return PaymentResponseDto.builder()
                .paymentId(payment.getId())
                .pgId(payment.getPgId())
                .totalPrice(payment.getTotalPrice())
                .userId(userId)
                .build();
    }
}
