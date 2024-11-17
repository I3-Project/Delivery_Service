package com.i3.delivery.domain.payment.dto;

import com.i3.delivery.domain.payment.entity.enums.PaymentStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PaymentStatusRequestDto {
    private PaymentStatusEnum paymentStatus;
    private Long orderId;
}
