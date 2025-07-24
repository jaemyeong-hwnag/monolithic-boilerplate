package com.hjm.monolithicboilerplate.common.exception;

/**
 * 비즈니스 로직 관련 예외 클래스입니다.
 * 도메인 규칙 위반이나 비즈니스 제약 조건 위반 시 사용됩니다.
 */
public class BusinessException extends BaseException {
    
    /**
     * CommonError를 받아 BusinessException을 생성합니다.
     */
    public BusinessException(ErrorCode error) {
        super(error);
    }
    
    /**
     * CommonError와 원인을 받아 BusinessException을 생성합니다.
     */
    public BusinessException(ErrorCode error, Throwable cause) {
        super(error, cause);
    }
    
    @Override
    public String getMessage() {
        return super.getMessage();
    }
} 