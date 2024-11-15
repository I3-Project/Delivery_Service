package com.i3.delivery.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uuid;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private int price;

    @Column
    private int stock;

    @Column
    private boolean isDeleted;

    @Column
    private String createdBy;

    @Column
    private String updatedBy;

    @Column
    private LocalDateTime deletedAt;

    @Column
    private String deletedBy;



}
