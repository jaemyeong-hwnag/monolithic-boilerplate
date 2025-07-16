package com.hjm.monolithicboilerplate.domain.user.entity;

import org.hibernate.annotations.Comment;

import com.hjm.monolithicboilerplate.domain.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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