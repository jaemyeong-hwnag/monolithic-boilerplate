package com.hjm.monolithicboilerplate.api.rest.domain.sample.dto.request;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserRequest {
    private final long id;
    private final String email;
    private final String name;
    private final LocalDateTime createdAt;
}

