package com.hjm.monolithicboilerplate.api.rest.domain.sample.controller;

import com.hjm.monolithicboilerplate.api.rest.common.ApiResponseAnnotation;
import com.hjm.monolithicboilerplate.api.rest.common.ApiResponseDto;
import com.hjm.monolithicboilerplate.api.rest.domain.sample.dto.request.CreateUserRequest;
import com.hjm.monolithicboilerplate.api.rest.domain.sample.dto.response.GetDateTimeResponse;
import com.hjm.monolithicboilerplate.api.rest.domain.sample.dto.response.GetUserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * REST API 공통 설정 테스트를 위한 샘플 컨트롤러
 */
@RestController
@RequestMapping("/api/sample")
public class SampleController {

    /**
     * 단일 사용자 조회 - 성공 케이스
     */
    @ApiResponseAnnotation(
            response200 = "사용자 정보 조회 성공",
            response404 = "사용자를 찾을 수 없음"
    )
    @GetMapping("/users/{id}")
    public GetUserResponse getUser(@PathVariable Long id) {
        // 실제로는 서비스에서 조회하지만, 샘플용으로 하드코딩
        if (id == 1L) {
            return GetUserResponse.builder()
                    .id(1L)
                    .email("test@example.com")
                    .name("테스트 사용자")
                    .createdAt(LocalDateTime.now())
                    .build();
        }
        // 404 에러를 시뮬레이션하기 위해 null 반환
        return null;
    }

    /**
     * 사용자 목록 조회 - 성공 케이스
     */
    @ApiResponseAnnotation(
            response200 = "사용자 목록 조회 성공"
    )
    @GetMapping("/users")
    public List<GetUserResponse> getUser() {
        return List.of(
                GetUserResponse.builder()
                        .id(1L)
                        .email("user1@example.com")
                        .name("사용자1")
                        .createdAt(LocalDateTime.now())
                        .build(),
                GetUserResponse.builder()
                        .id(2L)
                        .email("user2@example.com")
                        .name("사용자2")
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    /**
     * 사용자 생성 - 201 Created 응답
     */
    @ApiResponseAnnotation(
            response201 = "사용자가 성공적으로 생성됨"
    )
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserRequest createUser(@RequestBody CreateUserRequest request) {
        return CreateUserRequest.builder()
                .id(999L)
                .email(request.getEmail())
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    /**
     * 빈 응답 테스트
     */
    @ApiResponseAnnotation(
            response200 = "삭제 성공"
    )
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        // void 반환으로 빈 응답 테스트
    }

    /**
     * 문자열 응답 테스트
     */
    @ApiResponseAnnotation(
            response200 = "상태 확인 성공"
    )
    @GetMapping("/health")
    public ApiResponseDto<String> getHealth() {
        return ApiResponseDto.success("OK");
    }

    /**
     * 날짜/시간 포맷 테스트
     */
    @ApiResponseAnnotation(
            response200 = "날짜/시간 정보 조회 성공"
    )
    @GetMapping("/datetime")
    public GetDateTimeResponse getDateTime() {
        return GetDateTimeResponse.builder()
                .currentDate(LocalDate.now())
                .currentDateTime(LocalDateTime.now())
                .description("ObjectMapperConfig에서 설정한 포맷으로 직렬화됩니다")
                .build();
    }
} 