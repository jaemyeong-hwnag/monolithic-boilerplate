package com.hjm.monolithicboilerplate.api.rest.common;

import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

/**
 * Swagger 문서의 API 응답 상태 코드를 커스터마이징하는 컴포넌트
 */
@Component
public class ApiResponseCustomizer implements OperationCustomizer {

    /**
     * API 작업의 응답 상태 코드를 커스터마이징
     *
     * @param operation     Swagger 작업 객체
     * @param handlerMethod 컨트롤러 메서드 핸들러
     * @return 커스터마이징된 Swagger 작업 객체
     */
    @Override
    public Operation customize(
            Operation operation,
            HandlerMethod handlerMethod
    ) {
        // 메서드에 정의된 CustomApiResponseStatusCode 어노테이션 조회
        ApiResponseAnnotation annotation = AnnotatedElementUtils.findMergedAnnotation(
                handlerMethod.getMethod(),
                ApiResponseAnnotation.class
        );

        if (annotation == null) {
            return operation;
        }

        String mediaType = annotation.mediaType();

        // 응답 객체가 초기화되지 않은 경우 대비
        if (operation.getResponses() == null) {
            operation.setResponses(new ApiResponses());
        }

        // 200 응답이 비어있지 않으면 추가
        if (!annotation.response200().isBlank() &&
                !operation.getResponses().containsKey("200")) {
            operation.getResponses().addApiResponse("200", createApiResponse(annotation.response200(), mediaType));
        }

        // 201 응답이 비어있지 않으면 추가
        if (!annotation.response201().isBlank() &&
                !operation.getResponses().containsKey("201")) {
            operation.getResponses().addApiResponse("201", createApiResponse(annotation.response201(), mediaType));
        }

        // 404 응답이 비어있지 않으면 추가
        if (!annotation.response404().isBlank() &&
                !operation.getResponses().containsKey("404")) {
            operation.getResponses().addApiResponse("404", createApiResponse(annotation.response404(), mediaType));
        }

        // 공통 응답 상태 코드 추가
        if (!annotation.response404().isBlank() &&
                !operation.getResponses().containsKey("400")) {
            operation.getResponses().addApiResponse("400", createApiResponse("잘못된 요청", mediaType));
        }
        if (!annotation.response403().isBlank() &&
                !operation.getResponses().containsKey("403")) {
            operation.getResponses().addApiResponse("403", createApiResponse("권한 없음", mediaType));
        }

        return operation;
    }

    /**
     * API 응답 객체 생성
     * @return 생성된 API 응답 객체
     */
    private ApiResponse createApiResponse(String description, String mediaType) {
        return new ApiResponse()
                .description(description)
                .content(new Content().addMediaType(mediaType, new MediaType()));
    }
}
