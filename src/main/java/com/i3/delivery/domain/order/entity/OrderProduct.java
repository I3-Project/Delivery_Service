package com.i3.delivery.domain.order.entity;

import com.i3.delivery.domain.order.entity.enums.OrderStatusEnum;
import com.i3.delivery.domain.product.entity.Product;
import com.i3.delivery.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class OrderProduct extends BaseEntity {
    @Id
    @UuidGenerator
    private UUID orderFoodId;

    @Column(nullable = false)
    private Integer quantity;

    @Column
    private BigDecimal price;

    @Column
    private BigDecimal totalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus = OrderStatusEnum.PENDING;

    @Column(name = "delete_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedAt;

    @Column(name = "delete_by")
    private String deletedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public OrderProduct(Order orderId, Product productListId, Integer quantity) {
        this.quantity = quantity;
        this.price = productListId.getPrice();
        this.totalPrice = productListId.getPrice().multiply(BigDecimal.valueOf(quantity));
        this.order = orderId;
        this.product = productListId;
    }

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;*/


/*    public OrderProduct(Order orderId, Product productInfoId, Integer quantity) {
        this.quantity = quantity;
        this.price = productInfoId.getProductPrice();
        this.totalPrice = productInfoId.getProductPrice().multiply(BigDecimal.valueOf(quantity));
        this.order = orderId;
        this.product = productInfoId;
    }*/

    /* 주문 취소 */
    public void deleteOrderFood(String deletedBy) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = deletedBy;
    }

    @PreUpdate
    public void updateDeleteField(){
        if(orderStatus == orderStatus.CANCELED) {

            this.deletedAt = LocalDateTime.now();
            //this.deleteBy = getUserName();
        }
    }
}
