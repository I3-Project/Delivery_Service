package com.i3.delivery.domain.cart.controller;

import com.i3.delivery.domain.cart.dto.CartRequestDto;
import com.i3.delivery.domain.cart.dto.CartResponseDto;
import com.i3.delivery.domain.cart.service.CartService;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<CartResponseDto> createCart(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @RequestBody CartRequestDto cartRequestDTO) {
        CartResponseDto cartResponseDTO = cartService.createCart(userDetails.getUser().getId(), cartRequestDTO);
        return new ResponseEntity<>(cartResponseDTO, HttpStatus.CREATED);
    }

}
