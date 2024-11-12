package com.i3.delivery.store.entity;

import com.i3.delivery.store.dto.StoreEditRequsetDto;
import com.i3.delivery.store.dto.StoreRegistrationRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stores")
public class Store extends com.i3.delivery.store.entity.Timestamp {

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

//    @ManyToOne
//    @JoinColumn (name = "user_id")
//    @OnDelete(action = OnDeleteAction.SET_NULL)
//    private User ownerId;

    @Column
    private long ownerId;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private int totalReviews;

    @Column(nullable = false)
    private int ratingAvg;

//    @Column(nullable = false, name="created_at")
//    private Timestamp createdAt;

    @Column(nullable = false)
    private String createdBy;

//    @Column(name="updated_at")
//    private Timestamp updatedAt;

    @Column
    private String updatedBy;

    @Column(name="deleted_at")
    private Timestamp deletedAt;

    //preremove
    @Column
    private String deletedBy;

    public Store(StoreRegistrationRequestDto storeRegistrationRequestDto) {
        this.uuid = UUID.randomUUID().toString(); //랜덤으로 아이디 생성해서 구분
        this.name = storeRegistrationRequestDto.getName();
        this.description = storeRegistrationRequestDto.getDescription();
        this.category = storeRegistrationRequestDto.getCategory();
        this.address = storeRegistrationRequestDto.getAddress();
        this.phoneNumber = storeRegistrationRequestDto.getPhoneNumber();
        this.status = storeRegistrationRequestDto.getStatus();
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
    }

//    @OneToMany(mappedBy = "products")
//    @JsonIgnore
//    private List<Columns> columnsList = new ArrayList<>();


}
