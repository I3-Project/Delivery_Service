package com.i3.delivery.domain.store.entity;

import com.i3.delivery.domain.BaseEntity;
import com.i3.delivery.domain.store.dto.StoreEditRequsetDto;
import com.i3.delivery.domain.store.dto.StoreRegistrationRequestDto;
import com.i3.delivery.domain.store.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    private long id;

    @Column
    private String uuid;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private String category;

    @Column
    private long ownerId;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    //삭제플래그 추가
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private int totalReviews;

    @Column(nullable = false)
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

//    @OneToMany(mappedBy = "products")
//    @JsonIgnore
//    private List<Columns> columnsList = new ArrayList<>();

    @PreUpdate
    public void updateDeleteField(){
        if(status == Status.DELETED) {

            this.deletedAt = LocalDateTime.now();
            this.deletedBy = id;
        }
    }

    public Store(StoreRegistrationRequestDto storeRegistrationRequestDto) {
        this.uuid = UUID.randomUUID().toString();
        this.name = storeRegistrationRequestDto.getName();
        this.description = storeRegistrationRequestDto.getDescription();
        this.category = storeRegistrationRequestDto.getCategory();
        this.address = storeRegistrationRequestDto.getAddress();
        this.phoneNumber = storeRegistrationRequestDto.getPhoneNumber();
        this.status = Status.CLOSE;
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
        this.status = Status.DELETED;
    }
}
