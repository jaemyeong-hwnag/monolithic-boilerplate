package com.hjm.monolithicboilerplate.api.rest.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 사용자 목록 응답 DTO
 * <p>
 * 사용자 목록을 클라이언트에게 전달하기 위한 응답 DTO입니다.
 *
 * @author HJM
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 목록 응답")
public class UserListResponse {

    @Schema(description = "사용자 목록")
    private List<UserResponse> users;

    @Schema(description = "전체 사용자 수", example = "10")
    private int totalCount;
} 