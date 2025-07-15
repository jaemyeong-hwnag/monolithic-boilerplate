package com.hjm.monolithicboilerplate.api.rest.common;


import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;

/**
 * ApiResponse 응답 처리 어드바이스
 */
@RestControllerAdvice(basePackages = "com.hjm.monolithicboilerplate.api.rest.domain")
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // Swagger/OpenAPI 관련 엔드포인트는 제외
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String requestURI = request.getRequestURI();
                
                // Swagger/OpenAPI 관련 경로는 래핑하지 않음
                if (requestURI.startsWith("/v3/api-docs") || 
                    requestURI.startsWith("/swagger-ui") ||
                    requestURI.startsWith("/swagger-resources") ||
                    requestURI.startsWith("/webjars")) {
                    return false;
                }
            }
        } catch (Exception e) {
            // 예외 발생 시 기본적으로 래핑 허용
        }
        
        // byte[] 타입이면 감싸지 않음
        // String 타입도 감싸지 않음 (StringHttpMessageConverter와 충돌 방지)
        return !returnType.getParameterType().equals(byte[].class) 
            && !returnType.getParameterType().equals(String.class);
    }

    /**
     * ApiResponse의 status 필드를 HTTP 상태 코드로 동기화
     */
    @Override
    public ApiResponseDto<?> beforeBodyWrite(
            Object body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response
    ) {
        if (body == null) {
            return ApiResponseDto.success();
        }

        if (body instanceof ApiResponseDto<?>) {
            return (ApiResponseDto<?>) body;
        }



        return ApiResponseDto.success(body);
    }
}