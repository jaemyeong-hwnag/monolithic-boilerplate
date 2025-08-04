package com.hjm.monolithicboilerplate.api.rest.sample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hjm.monolithicboilerplate.api.rest.domain.sample.controller.SampleController;
import com.hjm.monolithicboilerplate.api.rest.domain.sample.dto.request.CreateUserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * SampleController 테스트 클래스
 * REST API 공통 설정이 제대로 작동하는지 확인
 */
@WebMvcTest(SampleController.class)
class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("단일 사용자 조회 - 성공 케이스")
    void getUser_Success() throws Exception {
        // given
        Long userId = 1L;

        // when & then
        mockMvc.perform(get("/api/sample/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.message").value("요청이 성공적으로 처리되었습니다."))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.email").value("test@example.com"))
                .andExpect(jsonPath("$.data.name").value("테스트 사용자"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("사용자 목록 조회 - 성공 케이스")
    void getUsers_Success() throws Exception {
        // when & then
        mockMvc.perform(get("/api/sample/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].email").value("user1@example.com"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].email").value("user2@example.com"));
    }

    @Test
    @DisplayName("사용자 생성 - 201 Created 응답")
    void createUser_Success() throws Exception {
        // given
        CreateUserRequest request = CreateUserRequest.builder()
                .email("new@example.com")
                .name("새 사용자")
                .build();

        // when & then
        mockMvc.perform(post("/api/sample/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.data.id").value(999))
                .andExpect(jsonPath("$.data.email").value("new@example.com"))
                .andExpect(jsonPath("$.data.name").value("새 사용자"));
    }

    @Test
    @DisplayName("빈 응답 테스트 - void 메서드")
    void deleteUser_Success() throws Exception {
        // when & then
        mockMvc.perform(delete("/api/sample/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.data").value("empty"));
    }

    @Test
    @DisplayName("문자열 응답 테스트")
    void getHealth_Success() throws Exception {
        // when & then
        mockMvc.perform(get("/api/sample/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.data").value("OK"));
    }

    @Test
    @DisplayName("존재하지 않는 사용자 조회 - null 응답")
    void getUser_NotFound() throws Exception {
        // given
        Long userId = 999L; // 존재하지 않는 ID

        // when & then
        mockMvc.perform(get("/api/sample/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.data").value("empty")); // null이 빈 성공 응답으로 변환됨
    }
} 