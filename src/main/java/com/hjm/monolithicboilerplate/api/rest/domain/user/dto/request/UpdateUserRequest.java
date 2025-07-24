package com.hjm.monolithicboilerplate.api.rest.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자 정보 업데이트 요청 DTO
 * 
 * 사용자 정보 업데이트를 위한 요청 데이터를 담는 DTO입니다.
 * 
 * @author HJM
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 정보 업데이트 요청")
public class UpdateUserRequest {
    
    @NotBlank(message = "이름은 필수입니다.")
    @Size(min = 1, max = 50, message = "이름은 1자 이상 50자 이하여야 합니다.")
    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;
    
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Schema(description = "사용자 이메일", example = "user@example.com")
    private String email;
} 