package com.hjm.monolithicboilerplate.domain.user.entity;

import com.hjm.monolithicboilerplate.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class UserEntity extends BaseEntity {
    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("Id")
    private Long id;
    @Column(name = "name", nullable = false)
    @Comment("name")
    private String name;
    @Column(name = "email", nullable = false)
    @Comment("email")
    private String email;
    @Column(name = "password", nullable = false)
    @Comment("password")
    private String password;
}