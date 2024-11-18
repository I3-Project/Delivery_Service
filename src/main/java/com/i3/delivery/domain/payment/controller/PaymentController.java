package com.i3.delivery.domain.payment.controller;

import com.i3.delivery.domain.payment.dto.PaymentRequestDto;
import com.i3.delivery.domain.payment.dto.PaymentResponseDto;
import com.i3.delivery.domain.payment.dto.PaymentStatusRequestDto;
import com.i3.delivery.domain.payment.service.PaymentService;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
    @PostMapping("/payments/{paymentId}")
    public ResponseEntity<Void> cancelPayment(@PathVariable Long paymentId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        paymentService.cancelPayment(paymentId, userDetails.getUser().getId());

        return ResponseEntity.noContent().build();
    }

    /* 3. 결제 상태 변경 */
    @PreAuthorize("hasAnyAuthority('ROLE_MASTER', 'ROLE_OWNER')")
    @PutMapping("/payments/{paymentId}")
    public ResponseEntity<Void> modifyPaymentStatus(@RequestBody PaymentStatusRequestDto request,
                                                    @PathVariable Long paymentId,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        paymentService.modifyStatusPayment(request, paymentId, userDetails.getUser().getId());

        return ResponseEntity.noContent().build();
    }

    /* 4. 결제 목록 조회 (USER) */
    @PreAuthorize("hasAnyAuthority('ROLE_MASTER', 'ROLE_USER')")
    @GetMapping("/payments/user")
    public ResponseEntity<Page<PaymentResponseDto>> userPaymentList(
            @PageableDefault(page = 0, size = 10, sort = "createdAt",
            direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam Integer size,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Page<PaymentResponseDto> responseDto = paymentService.userPaymentList(pageable, size, userDetails.getUser().getId());

        return ResponseEntity.ok(responseDto);
    }

    /* 5. 결제 목록 조회 (storeId) */
    @PreAuthorize("hasAnyAuthority('ROLE_MASTER', 'ROLE_OWNER')")
    @GetMapping("/payments/{storeId}")
    public ResponseEntity<Page<PaymentResponseDto>> ownerPaymentList(
            @PageableDefault(page = 0, size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam Integer size,
            @PathVariable("storeId") Long storeId
    ) {
        Page<PaymentResponseDto> responseDto = paymentService.ownerPaymentList(pageable, size, storeId);

        return ResponseEntity.ok(responseDto);
    }

    /* 6. 결제 내역 삭제 */
    @PreAuthorize("hasAnyAuthority('ROLE_MASTER', 'ROLE_USER', 'ROLE_MANAGER')")
    @DeleteMapping("/payments/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long paymentId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        paymentService.deletePayment(paymentId, userDetails.getUser().getId());

        return ResponseEntity.noContent().build();
    }

}
