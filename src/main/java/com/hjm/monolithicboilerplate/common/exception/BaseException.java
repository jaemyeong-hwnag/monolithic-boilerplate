package com.hjm.monolithicboilerplate.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 기본 예외 클래스
 * 
 * 모든 비즈니스 예외의 기본이 되는 클래스입니다.
 * CommonError를 구현하여 표준화된 에러 정보를 제공합니다.
 */
public abstract class BaseException extends RuntimeException {
    
    /**
     * 기본 생성자
     */
    BaseException() {
        super();
    }
    
    /**
     * 메시지를 포함한 생성자
     * 
     * @param message 예외 메시지
     */
    BaseException(String message) {
        super(message);
    }

    /**
     * 메시지와 원인을 포함한 생성자
     * 
     * @param message 예외 메시지
     * @param cause 원인 예외
     */
    BaseException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * CommonError를 포함한 생성자
     * 
     * @param error 공통 에러
     */
    BaseException(ErrorCode error) {
        super(error.getMessage());
    }
    
    /**
     * CommonError와 원인을 포함한 생성자
     * 
     * @param error 공통 에러
     * @param cause 원인 예외
     */
    BaseException(ErrorCode error, Throwable cause) {
        super(error.getMessage(), cause);
    }
} 