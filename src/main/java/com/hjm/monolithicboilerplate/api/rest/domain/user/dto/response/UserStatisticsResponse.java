package com.hjm.monolithicboilerplate.api.rest.domain.user.dto.response;

import com.hjm.monolithicboilerplate.app.user.facade.UserFacade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자 통계 응답 DTO
 * 
 * 사용자 통계 정보를 클라이언트에게 전달하기 위한 응답 DTO입니다.
 * 
 * @author HJM
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 통계 응답")
public class UserStatisticsResponse {
    
    @Schema(description = "전체 사용자 수", example = "100")
    private long totalUsers;
    
    @Schema(description = "활성 사용자 수", example = "80")
    private long activeUsers;
    
    @Schema(description = "비활성 사용자 수", example = "15")
    private long inactiveUsers;
    
    @Schema(description = "삭제된 사용자 수", example = "5")
    private long deletedUsers;
    
    /**
     * UserFacade.UserStatistics로부터 UserStatisticsResponse를 생성합니다.
     * 
     * @param statistics UserFacade.UserStatistics 객체
     * @return UserStatisticsResponse 객체
     */
    public static UserStatisticsResponse from(UserFacade.UserStatistics statistics) {
        return UserStatisticsResponse.builder()
                .totalUsers(statistics.getTotalUsers())
                .activeUsers(statistics.getActiveUsers())
                .inactiveUsers(statistics.getInactiveUsers())
                .deletedUsers(statistics.getDeletedUsers())
                .build();
    }
} 