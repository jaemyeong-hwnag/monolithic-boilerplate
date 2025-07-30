package com.hjm.monolithicboilerplate.domain.user.vo;

import com.hjm.monolithicboilerplate.domain.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    private final UserId id;
    private final String name;
    private final String email;

    public static User from(UserEntity userEntity) {
        return User.builder()
                .id(new UserId(userEntity.getId()))
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .build();
    }
}
