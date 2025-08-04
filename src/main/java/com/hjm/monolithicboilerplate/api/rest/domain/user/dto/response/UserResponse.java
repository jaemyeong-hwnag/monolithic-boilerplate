package com.hjm.monolithicboilerplate.api.rest.domain.user.dto.response;

import com.hjm.monolithicboilerplate.domain.user.vo.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * 사용자 응답 DTO
 */
@Getter
@Schema(description = "사용자 응답")
@Builder
public class UserResponse {
    
    @Schema(description = "사용자 ID", example = "1")
    private Long id;
    
    @Schema(description = "사용자 이메일", example = "user@example.com")
    private String email;
    
    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId().getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}