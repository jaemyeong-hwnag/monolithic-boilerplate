package com.hjm.monolithicboilerplate.api.rest.common;

import java.lang.annotation.*;

/**
 * API 응답 상태 코드에 대한 설명을 정의하는 커스텀 어노테이션<p>
 * Swagger 문서화에서 API 응답 상태 코드별 설명을 커스터마이징하기 위해 사용<p>
 *
 * 사용 예시:<p>
 * {@code
 * @ApiResponseAnnotation(
 *     response200 = "성공적으로 조회됨",
 *     response201 = "성공적으로 생성됨",
 *     response404 = "요청한 리소스를 찾을 수 없음"
 * )
 * }
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiResponseAnnotation {

    /**
     * HTTP 200 OK 응답에 대한 설명
     * 요청이 성공적으로 처리되었음을 나타내는 응답
     *
     * @return 200 응답에 대한 설명 문자열
     */
    String response200() default "";

    /**
     * HTTP 201 Created 응답에 대한 설명
     * 새로운 리소스가 성공적으로 생성되었음을 나타내는 응답
     *
     * @return 201 응답에 대한 설명 문자열
     */
    String response201() default "";

    /**
     * HTTP 404 Not Found 응답에 대한 설명
     * 요청한 리소스를 찾을 수 없음을 나타내는 응답
     *
     * @return 404 응답에 대한 설명 문자열
     */
    String response404() default "";

    /**
     * HTTP 403 Forbidden 응답에 대한 설명
     * 요청이 권한이 없거나 금지된 경우를 나타내는 응답
     *
     * @return 403 응답에 대한 설명 문자열
     */
    String response403() default "";

    /**
     * mediaType 설정 default application/json
     *
     * @return mediaType
     */
    String mediaType() default "application/json";
}
