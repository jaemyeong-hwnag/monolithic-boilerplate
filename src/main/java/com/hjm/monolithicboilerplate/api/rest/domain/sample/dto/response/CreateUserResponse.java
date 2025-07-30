package com.hjm.monolithicboilerplate.api.rest.domain.sample.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateUserResponse {
    private final long id;
    private final String email;
    private final String name;
    private final LocalDateTime createdAt;
}
