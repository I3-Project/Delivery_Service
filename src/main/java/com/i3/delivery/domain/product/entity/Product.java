package com.i3.delivery.domain.product.entity;

import com.i3.delivery.domain.product.dto.ProductEditRequestDto;
import com.i3.delivery.domain.product.enums.ProductStatus;
import com.i3.delivery.domain.store.entity.Store;
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
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="p_products")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private UUID uuid;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private BigDecimal price;

    @Column
    private int stock;

    @Column
    private ProductStatus status;

    @Column
    private LocalDateTime deletedAt;

    @Column
    private String deletedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    public void createStoreField(){
        this.setCreatedBy(getUserName());
    }

    @PreUpdate
    public void updateDeleteField(){
        if(status == ProductStatus.DELETED) {

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


    public void update(ProductEditRequestDto productEditRequestDto) {

        this.name = productEditRequestDto.getName();
        this.description = productEditRequestDto.getDescription();
        this.price = productEditRequestDto.getPrice();
        this.stock = productEditRequestDto.getStock();
        this.status = productEditRequestDto.getStatus();
    }

    public void delete() {
        this.status = ProductStatus.DELETED;
    }

    public void deleteAll() {

    }
}
