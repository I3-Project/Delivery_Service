package com.i3.delivery.domain.user.entity;

import com.i3.delivery.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "p_users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    @Column
    private String address;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column
    private Boolean is_deleted;

    @Column
    private String updated_by;

    @Column
    private String deleted_at;

    @Column
    private String deleted_by;

    @Builder
    public User(String username, String password, String nickname, String email, String phone, String address, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role != null ? role : UserRoleEnum.USER; // 기본값 설정
        this.is_deleted = false;
    }

}
