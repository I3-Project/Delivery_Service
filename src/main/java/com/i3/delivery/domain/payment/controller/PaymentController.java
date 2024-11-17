package com.i3.delivery.domain.payment.controller;

import com.i3.delivery.domain.payment.dto.PaymentRequestDto;
import com.i3.delivery.domain.payment.dto.PaymentResponseDto;
import com.i3.delivery.domain.payment.dto.PaymentStatusRequestDto;
import com.i3.delivery.domain.payment.service.PaymentService;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /* 1. 결제 생성 */
    @PreAuthorize("hasAnyAuthority('ROLE_MASTER', 'ROLE_USER')")
    @PostMapping("/payments")
    public ResponseEntity<PaymentResponseDto> createPayment(@RequestBody PaymentRequestDto request,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        PaymentResponseDto responseDto = paymentService.createPayment(request, userDetails.getUser().getId());
        return ResponseEntity.ok(responseDto);
    }

    /* 2. 결제 취소 */
    @PreAuthorize("hasAnyAuthority('ROLE_MASTER', 'ROLE_USER')")
    @DeleteMapping("payments/{paymentId}")
    public ResponseEntity<Void> cancelPayment(@PathVariable Long paymentId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        paymentService.cancelPayment(paymentId, userDetails.getUser().getId());

        return ResponseEntity.noContent().build();
    }

    /* 3. 결제 상태 변경 */
    @PutMapping("/payments/{paymentId}")
    public ResponseEntity<Void> modifyPaymentStatus(@RequestBody PaymentStatusRequestDto request,
                                                   @PathVariable Long paymentId
    ) {
        paymentService.modifyStatusPayment(request, paymentId);

        return ResponseEntity.noContent().build();
    }
}
