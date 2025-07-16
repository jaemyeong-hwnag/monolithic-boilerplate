package com.hjm.monolithicboilerplate.common.exception;

/**
 * 시스템 관련 예외 클래스입니다.
 * 외부 시스템 연동 실패나 시스템 오류 시 사용됩니다.
 */
public class SystemException extends BaseException {
    
    /**
     * CommonError를 받아 SystemException을 생성합니다.
     */
    public SystemException(ErrorCode error) {
        super(error);
    }
    
    /**
     * CommonError와 원인을 받아 SystemException을 생성합니다.
     */
    public SystemException(ErrorCode error, Throwable cause) {
        super(error, cause);
    }
    
    @Override
    public String getMessage() {
        return super.getMessage();
    }
} 