package com.i3.delivery.domain.store.entity;

import com.i3.delivery.domain.category.entity.Category;
import com.i3.delivery.domain.product.entity.Product;
import com.i3.delivery.domain.review.entity.Review;
import com.i3.delivery.domain.store.dto.StoreEditRequsetDto;
import com.i3.delivery.domain.store.enums.StoreStatus;
import com.i3.delivery.domain.user.entity.User;
import com.i3.delivery.domain.user.security.UserDetailsImpl;
import com.i3.delivery.global.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_stores")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private UUID uuid;

    @Size(min = 2, max = 20, message = "이름은 최소 2자입니다.")
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Size(max = 500, message = "설명은 최대 500자입니다.")
    @NotBlank(message = "설명을 입력해주세요.")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Size(max = 50, message = "주소는 최대 50자입니다.")
    private String address;

    @Size(max = 20, message = "번호는 최대 20자입니다.")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @Column(nullable = false)
    private int totalReviews;

    @Column(nullable = false)
    @Min(-1)@Max(5)
    private int ratingAvg;

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

    @Column(name="deleted_by")
    private String deletedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "store")
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Product> productList = new ArrayList<>();


    @PrePersist
    public void createStoreField(){
            this.setCreatedBy(getUserName());
    }

    @PreUpdate
    public void updateDeleteField(){
        if(status == StoreStatus.DELETED) {

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

    public void update(StoreEditRequsetDto storeEditRequsetDto,Category category) {
        this.name = storeEditRequsetDto.getName();
        this.description = storeEditRequsetDto.getDescription();
        this.category= category;
        this.address = storeEditRequsetDto.getAddress();
        this.phoneNumber = storeEditRequsetDto.getPhoneNumber();
        this.status = storeEditRequsetDto.getStatus();
        this.totalReviews = storeEditRequsetDto.getTotalReviews();
        this.ratingAvg = storeEditRequsetDto.getRatingAvg();
    }

    public void delete() {
        this.status = StoreStatus.DELETED;
    }
}
