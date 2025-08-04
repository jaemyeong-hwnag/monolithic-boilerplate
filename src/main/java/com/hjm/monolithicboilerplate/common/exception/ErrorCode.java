package com.hjm.monolithicboilerplate.common.exception;

/**
 * 공통 에러 인터페이스
 * <p>
 * 모든 도메인 에러가 구현해야 하는 공통 인터페이스입니다.
 * 에러 코드와 메시지를 표준화된 방식으로 제공합니다.
 */
public interface ErrorCode {
    String getCode();

    String getMessage();
}