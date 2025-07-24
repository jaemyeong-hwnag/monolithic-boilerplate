package com.hjm.monolithicboilerplate.api.rest.config;

import java.time.LocalDate;
import java.time.LocalDateTime;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!prod")
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Monolithic Boilerplate API")
                        .description("모놀리식 보일러플레이트 REST API 문서")
                        .version("1.0.0")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("HJM Team")
                                .email("woaudsla@gmail.com")))
                .servers(java.util.List.of(
                        new io.swagger.v3.oas.models.servers.Server()
                                .url("http://localhost:8080")
                                .description("로컬 개발 서버")
                ));
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            this.addResponseBodyWrapperSchemaExample(operation);
            return operation;
        };
    }

    @Bean
    public PropertyCustomizer localDateTimePropertyCustomizer() {
        return (schema, type) -> {
            if (type.getType() == LocalDateTime.class) {
                schema.example("2025-07-16 12:34:56");
                schema.description("날짜시간 (yyyy-MM-dd HH:mm:ss)");
            }
            if (type.getType() == LocalDate.class) {
                schema.example("2025-07-16");
                schema.description("날짜 (yyyy-MM-dd)");
            }
            return schema;
        };
    }

    private void addResponseBodyWrapperSchemaExample(Operation operation) {
        if (operation == null || operation.getResponses() == null) return;

        final String successCode = "SUCCESS";
        final String successMessage = "요청이 성공적으로 처리되었습니다.";
        final Boolean successValue = true;

        operation.getResponses().forEach((code, apiResponse) -> {
            Content content = apiResponse.getContent();
            if (content == null) return;

            content.forEach((mediaTypeKey, mediaType) -> {
                Schema<?> originalSchema = mediaType.getSchema();
                if (originalSchema == null || isWrappedWithCommonResponse(originalSchema)) {
                    return;
                }

                String exampleCode;
                String exampleMessage;
                Boolean exampleSuccess;
                try {
                    if (code.equals("default")) {
                        exampleCode = successCode;
                        exampleMessage = successMessage;
                        exampleSuccess = successValue;
                    } else {
                        int status = Integer.parseInt(code);
                        if (status >= 200 && status < 300) {
                            exampleCode = successCode;
                            exampleMessage = successMessage;
                            exampleSuccess = successValue;
                        } else {
                            // 실패 응답 예시
                            exampleCode = code;
                            exampleMessage = "요청이 실패하였습니다.";
                            exampleSuccess = false;
                        }
                    }
                } catch (NumberFormatException e) {
                    exampleCode = code;
                    exampleMessage = "요청이 실패하였습니다.";
                    exampleSuccess = false;
                }

                Schema<?> wrappedSchema = wrapSchema(
                        originalSchema,
                        exampleCode,
                        exampleMessage,
                        exampleSuccess
                );
                mediaType.setSchema(wrappedSchema);
            });
        });
    }

    /**
     * 원본 스키마를 ApiResponseDto 형태로 래핑합니다.
     *
     * @param originalSchema 원본 스키마
     * @param exampleCode    예시 코드
     * @param exampleMessage 예시 메시지
     * @param exampleSuccess 예시 성공 여부
     * @return 래핑된 스키마
     */
    private Schema<?> wrapSchema(
            Schema<?> originalSchema,
            String exampleCode,
            String exampleMessage,
            Boolean exampleSuccess
    ) {
        final Schema<?> wrapperSchema = new Schema<>();
        wrapperSchema.setType("object");

        // success 필드
        Schema<Boolean> successSchema = new Schema<>();
        successSchema.setType("boolean");
        successSchema.setExample(exampleSuccess);
        successSchema.setDescription("성공 여부");
        wrapperSchema.addProperty("success", successSchema);

        // code 필드
        Schema<String> codeSchema = new Schema<>();
        codeSchema.setType("string");
        codeSchema.setExample(exampleCode);
        codeSchema.setDescription("응답 코드");
        wrapperSchema.addProperty("code", codeSchema);

        // message 필드
        Schema<String> messageSchema = new Schema<>();
        messageSchema.setType("string");
        messageSchema.setExample(exampleMessage);
        messageSchema.setDescription("응답 메시지");
        wrapperSchema.addProperty("message", messageSchema);

        // data 필드 (원본 스키마 유지)
        if (originalSchema != null) {
            originalSchema.setDescription("실제 응답 데이터");
            wrapperSchema.addProperty("data", originalSchema);
        } else {
            // 원본 스키마가 null인 경우 빈 객체로 설정
            Schema<Object> emptySchema = new Schema<>();
            emptySchema.setType("object");
            emptySchema.setDescription("실제 응답 데이터");
            wrapperSchema.addProperty("data", emptySchema);
        }

        // timestamp 필드
        Schema<String> timestampSchema = new Schema<>();
        timestampSchema.setType("string");
        timestampSchema.setFormat("date-time");
        timestampSchema.setExample("2025-07-16 12:34:56");
        timestampSchema.setDescription("응답 시각 (KST)");
        wrapperSchema.addProperty("timestamp", timestampSchema);

        return wrapperSchema;
    }

    /**
     * 이미 래핑되어 있는지 확인합니다.
     */
    private boolean isWrappedWithCommonResponse(Schema<?> schema) {
        if (schema == null) return false;
        String ref = schema.get$ref();
        if (ref != null) return ref.contains("CommonResponse") || ref.contains("ApiResponseDto");
        if ("object".equals(schema.getType())) {
            return schema.getProperties() != null &&
                    schema.getProperties().containsKey("code") &&
                    schema.getProperties().containsKey("data") &&
                    schema.getProperties().containsKey("success") &&
                    schema.getProperties().containsKey("message") &&
                    schema.getProperties().containsKey("timestamp");
        }
        return false;
    }
}
