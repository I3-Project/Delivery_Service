package com.i3.delivery.domain.payment.dto;

import com.i3.delivery.domain.payment.entity.enums.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDto {
    private Long orderId;
    private String pgId;
}
