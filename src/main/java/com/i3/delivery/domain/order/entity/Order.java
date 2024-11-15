package com.i3.delivery.domain.order.entity;

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
    // TODO UUID -> IDENTITY (db 도 auto increment)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // TODO Long
    private Long id;

    @Column(nullable = false)
    private UUID orderUId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderTypeEnum orderType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus = OrderStatusEnum.PENDING;

    @Column
    private BigDecimal totalPrice;

    @Column
    @Size(max = 60, message = "요청 사항은 60글자 이내로 작성해주세요.")
    private String oRequest;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "delete_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedAt;

    @Column(name = "delete_by")
    private String deletedBy;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProductList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /*@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;


    @PreUpdate
    public void updateDeleteField(){
        if(orderStatus == orderStatus.DELETED) {
            this.deletedAt = LocalDateTime.now();
            this.deletedBy = getUserName();
        }
    }


   private static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetailsImpl) {
            return userDetailsImpl.getUser().getNickname();
        }
        return null;
    }

   /* 주문 등록 */
   public Order(User user, Store store, String orderType, String oRequest) {
        this.user = user;
        this.store = store;
        this.orderType = OrderTypeEnum.valueOf(orderType);
        this.totalPrice = BigDecimal.ZERO;
        this.oRequest = oRequest;
    }
}
