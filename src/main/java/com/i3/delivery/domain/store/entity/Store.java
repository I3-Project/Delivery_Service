package com.i3.delivery.domain.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.i3.delivery.domain.review.entity.Review;
import com.i3.delivery.domain.store.dto.StoreEditRequsetDto;
import com.i3.delivery.domain.store.dto.StoreRegistrationRequestDto;
import com.i3.delivery.domain.store.enums.StoreStatus;
import com.i3.delivery.global.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
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

    @Size(max = 20, message = "카테고리는 최대 20자입니다.")
    private String category;

    @Column
    private long ownerId;

    @Size(max = 50, message = "주소는 최대 50자입니다.")
    private String address;

    @Size(max = 20, message = "번호는 최대 20자입니다.")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private StoreStatus status;

    @Column
    private int totalReviews;

    @Column(nullable = false)
    @Min(1)@Max(5)
    private int ratingAvg;

    @Column(nullable = false)
    private String createdBy;

    @Column
    private String updatedBy;

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

    @Column(name="deleted_by")
    private long deletedBy;

//    @ManyToOne
//    @JoinColumn (name = "user_id")
//    @OnDelete(action = OnDeleteAction.SET_NULL)
//    private User ownerId;

    @OneToMany(mappedBy = "store")
    @JsonIgnore
    private List<Review> reviewList = new ArrayList<>();

    @PreUpdate
    public void updateDeleteField(){
        if(status == com.i3.delivery.domain.store.enums.StoreStatus.DELETED) {

            this.deletedAt = LocalDateTime.now();
            //this.deletedBy = id;
        }
    }

    public Store(StoreRegistrationRequestDto storeRegistrationRequestDto) {
        this.uuid = UUID.fromString(UUID.randomUUID().toString());
        this.name = storeRegistrationRequestDto.getName();
        this.description = storeRegistrationRequestDto.getDescription();
        this.category = storeRegistrationRequestDto.getCategory();
        this.address = storeRegistrationRequestDto.getAddress();
        this.phoneNumber = storeRegistrationRequestDto.getPhoneNumber();
        this.status = com.i3.delivery.domain.store.enums.StoreStatus.CLOSE;
        this.totalReviews = storeRegistrationRequestDto.getTotalReviews();
        this.ratingAvg = storeRegistrationRequestDto.getRatingAvg();
        this.createdBy = storeRegistrationRequestDto.getCreatedBy();
    }

    public void update(StoreEditRequsetDto storeEditRequsetDto) {
        this.name = storeEditRequsetDto.getName();
        this.description = storeEditRequsetDto.getDescription();
        this.category = storeEditRequsetDto.getCategory();
        this.address = storeEditRequsetDto.getAddress();
        this.phoneNumber = storeEditRequsetDto.getPhoneNumber();
        this.status = storeEditRequsetDto.getStatus();
        this.totalReviews = storeEditRequsetDto.getTotalReviews();
        this.ratingAvg = storeEditRequsetDto.getRatingAvg();
        this.updatedBy = "me";
//        this.updatedBy = storeEditRequsetDto.getUser();
    }

    public void delete(Long id) {
        this.status = com.i3.delivery.domain.store.enums.StoreStatus.DELETED;
    }
}
