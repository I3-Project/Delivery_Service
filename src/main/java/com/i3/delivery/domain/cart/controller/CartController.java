package com.i3.delivery.domain.cart.controller;

import com.i3.delivery.domain.cart.dto.CartRequestDto;
import com.i3.delivery.domain.cart.dto.CartResponseDto;
import com.i3.delivery.domain.cart.dto.CartUpdateRequestDto;
import com.i3.delivery.domain.cart.entity.Cart;
import com.i3.delivery.domain.cart.service.CartService;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    // 장바구니 등록
    @PostMapping("/cart")
    public ResponseEntity<CartResponseDto> createCart(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @RequestBody CartRequestDto cartRequestDTO) {
        Long userId = userDetails.getUser().getId();
        CartResponseDto cartResponseDTO = cartService.createCart(userId, cartRequestDTO);
        return new ResponseEntity<>(cartResponseDTO, HttpStatus.CREATED);
    }

    // 장바구니 목록
    @GetMapping("/cart")
    public ResponseEntity<List<CartResponseDto>> getCartsByUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        List<CartResponseDto> carts = cartService.getCartsByUserId(userId);
        return ResponseEntity.ok(carts);
    }

    // 장바구니 개수 수정
    @PatchMapping("/cart/{cartId}")
    public ResponseEntity<String> updateCart(@PathVariable Long cartId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @RequestBody CartUpdateRequestDto requestDto) {
        Long userId = userDetails.getUser().getId();
        Cart updatedCart = cartService.updateCart(cartId, userId, requestDto);
        return ResponseEntity.ok("카트 수량 수정완료");
    }

    // 장바구니 삭제
    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long cartId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        cartService.deleteCart(cartId, userId);
        return ResponseEntity.ok("카트 삭제완료");
    }
}
