package com.hjm.monolithicboilerplate.api.rest.domain.sample.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

/**
 * 날짜/시간 응답 DTO
 */
@Getter
@Builder
public class GetDateTimeResponse {
    private final LocalDate currentDate;
    private final LocalDateTime currentDateTime;
    private final String description;
}
