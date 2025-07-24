package com.hjm.monolithicboilerplate.api.rest.domain.user.controller;

import com.hjm.monolithicboilerplate.api.rest.common.ApiResponseDto;
import com.hjm.monolithicboilerplate.api.rest.domain.sample.dto.response.CreateUserResponse;
import com.hjm.monolithicboilerplate.api.rest.domain.user.dto.request.CreateUserRequest;
import com.hjm.monolithicboilerplate.api.rest.domain.user.dto.response.UserResponse;
import com.hjm.monolithicboilerplate.domain.user.entity.UserEntity;
import com.hjm.monolithicboilerplate.app.user.facade.UserFacade;
import com.hjm.monolithicboilerplate.domain.user.vo.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * User 도메인 REST API Controller
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "사용자 관리 API")
public class UserController {
    
    private final UserFacade userFacade;

    /**
     * ID로 사용자를 조회합니다.
     *
     * @param id 조회할 사용자 ID
     * @return 사용자 정보
     */
    @GetMapping("/{id}")
    @Operation(summary = "사용자 조회", description = "ID로 사용자를 조회합니다.")
    public UserResponse getUserById(
        @Parameter(description = "사용자 ID") @PathVariable Long id) {
        log.debug("사용자 조회 API 호출: id={}", id);

        User user = userFacade.getUserById(id);

        return UserResponse.from(user);
    }
} 