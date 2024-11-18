package com.i3.delivery.domain.cart.service;

import com.i3.delivery.domain.cart.dto.CartRequestDto;
import com.i3.delivery.domain.cart.dto.CartResponseDto;
import com.i3.delivery.domain.cart.dto.CartUpdateRequestDto;
import com.i3.delivery.domain.cart.entity.Cart;
import com.i3.delivery.domain.cart.repository.CartRepository;
import com.i3.delivery.domain.product.entity.Product;
import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.user.entity.User;
import com.i3.delivery.domain.product.repository.ProductRepository;
import com.i3.delivery.domain.store.repository.StoreRepository;
import com.i3.delivery.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;


    @Transactional
    public CartResponseDto createCart(Long userId, CartRequestDto cartRequestDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다."));

        Product product = productRepository.findById(cartRequestDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품 정보가 없습니다."));

        Store store = storeRepository.findById(cartRequestDTO.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("가게 정보가 없습니다."));

        Cart cart = Cart.builder()
                .user(user)
                .product(product)
                .store(store)
                .quantity(cartRequestDTO.getQuanity())
                .build();

        Cart savedCart = cartRepository.save(cart);

        return CartResponseDto.builder()
                .id(savedCart.getId())
                .userId(savedCart.getUser().getId())
                .productId(savedCart.getProduct().getId())
                .storeId(savedCart.getStore().getId())
                .quantity(savedCart.getQuantity())
                .build();
    }

    @Transactional(readOnly = true)
    public List<CartResponseDto> getCartsByUserId(Long userId) {
        return cartRepository.findAllByUserId(userId).stream()
                .map(CartResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Cart updateCart(Long cartId, Long userId, CartUpdateRequestDto requestDto) {
        Cart cart = cartRepository.findByIdAndUserId(cartId, userId);
        if (cart == null) {
            throw new IllegalArgumentException("해당 카트를 찾을 수 없습니다.");
        }
        cart.setQuantity(requestDto.getQuantity());
        cartRepository.save(cart);

        return cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(Long cartId, Long userId) {
        Cart cart = cartRepository.findByIdAndUserId(cartId, userId);
        if (cart == null) {
            throw new IllegalArgumentException("해당 카트를 찾을 수 없습니다.");
        }
        cartRepository.delete(cart);
    }
}
