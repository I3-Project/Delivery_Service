package com.i3.delivery.domain.payment.entity;

import com.i3.delivery.domain.order.entity.Order;
import com.i3.delivery.domain.payment.entity.enums.PaymentStatusEnum;
import com.i3.delivery.domain.review.entity.ReviewStatusEnum;
import com.i3.delivery.domain.user.entity.User;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import com.i3.delivery.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="p_payment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column
    private UUID paymentUId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum paymentStatus;

    @Column
    private BigDecimal totalPrice;

    @Column(name = "pg_id", nullable = false)
    private String pgId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedAt;

    @Column(name = "deleted_by")
    private String deletedBy;

    @PreUpdate
    public void updateDeleteField(){
        if (paymentStatus == PaymentStatusEnum.DELETED) {
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
    @Builder
    public Payment(Order order, BigDecimal totalPrice, PaymentStatusEnum paymentStatus, String pgId, User user) {
        this.order = order;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
        this.pgId = pgId;
        this.user = user;
    }

}
