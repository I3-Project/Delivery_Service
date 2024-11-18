package com.i3.delivery.domain.order.entity;

import com.i3.delivery.domain.cart.entity.Cart;
import com.i3.delivery.domain.order.entity.enums.OrderStatusEnum;
import com.i3.delivery.domain.order.entity.enums.OrderTypeEnum;
import com.i3.delivery.domain.store.entity.Store;
import com.i3.delivery.domain.user.entity.User;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import com.i3.delivery.global.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "p_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderTypeEnum orderType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;

    @Column
    private BigDecimal totalPrice;

    @Column
    @Size(max = 60, message = "요청 사항은 60글자 이내로 작성해주세요.")
    private String orderRequest;

    @Size(max = 50, message = "주소는 최대 50자입니다.")
    private String address;

    @Column
    private String productName;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    private String deletedBy;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Cart> cartList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @PrePersist
    public void createField(){
        this.setCreatedBy(getUserName());
    }

    @PreUpdate
    public void updateDeleteField(){
        if(orderStatus == OrderStatusEnum.CANCELED) {
            this.deletedAt = LocalDateTime.now();
            this.deletedBy = getUserName();
        }
        this.setUpdatedAt(LocalDateTime.now());
        this.setUpdatedBy(getUserName());
    }


   private static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetailsImpl) {
            return userDetailsImpl.getUser().getUsername();
        }
        return null;
    }

    @Builder
   /* 주문 등록 */
   public static Order createOrder(User user, Store store, String orderType, String orderRequest, BigDecimal totalPrice, String address) {
        return Order.builder()
                .user(user)
                .store(store)
                .orderType(OrderTypeEnum.valueOf(orderType))
                .orderRequest(orderRequest)
                .totalPrice(totalPrice)
                .uuid(UUID.randomUUID())
                .address(address)
                .orderStatus(OrderStatusEnum.PENDING)
                .build();
    }
}
