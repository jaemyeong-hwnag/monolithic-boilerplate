package com.hjm.monolithicboilerplate.api.rest.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ObjectMapperConfig 날짜/시간 포맷 테스트
 */
@JsonTest
@Import(ObjectMapperConfig.class)
class ObjectMapperConfigTest {

    @Autowired
    ObjectMapper objectMapper;

    static class SampleDateTime {
        public LocalDate date;
        public LocalDateTime dateTime;
        public SampleDateTime() {}
        public SampleDateTime(LocalDate date, LocalDateTime dateTime) {
            this.date = date;
            this.dateTime = dateTime;
        }
    }

    @Test
    @DisplayName("LocalDate, LocalDateTime 직렬화/역직렬화 포맷 검증")
    void testDateTimeFormat() throws JsonProcessingException {
        // given
        SampleDateTime sample = new SampleDateTime(
                LocalDate.of(2024, 7, 16),
                LocalDateTime.of(2024, 7, 16, 12, 34, 56)
        );

        // when
        String json = objectMapper.writeValueAsString(sample);
        // then
        assertThat(json).contains("\"date\":\"2024-07-16\"");
        assertThat(json).contains("\"dateTime\":\"2024-07-16 12:34:56\"");

        // 역직렬화 테스트
        String input = "{\"date\":\"2024-07-16\",\"dateTime\":\"2024-07-16 12:34:56\"}";
        SampleDateTime deserialized = objectMapper.readValue(input, SampleDateTime.class);
        assertThat(deserialized.date).isEqualTo(LocalDate.of(2024, 7, 16));
        assertThat(deserialized.dateTime).isEqualTo(LocalDateTime.of(2024, 7, 16, 12, 34, 56));
    }
} 