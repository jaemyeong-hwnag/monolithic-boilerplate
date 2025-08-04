package com.hjm.monolithicboilerplate.common.exception;

/**
 * 입력 검증 관련 예외 클래스입니다.
 * 요청 데이터의 유효성 검증 실패 시 사용됩니다.
 */
public class ValidationException extends BaseException {

    /**
     * CommonError를 받아 ValidationException을 생성합니다.
     */
    public ValidationException(ErrorCode error) {
        super(error);
    }

    /**
     * CommonError와 원인을 받아 ValidationException을 생성합니다.
     */
    public ValidationException(ErrorCode error, Throwable cause) {
        super(error, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
} 