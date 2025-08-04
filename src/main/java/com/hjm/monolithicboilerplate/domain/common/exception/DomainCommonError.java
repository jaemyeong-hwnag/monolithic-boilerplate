package com.hjm.monolithicboilerplate.domain.common.exception;

import com.hjm.monolithicboilerplate.common.exception.ErrorCode;

public enum DomainCommonError implements ErrorCode {
    INVALID_ID("INVALID_ID", "유효하지 않은 ID입니다."),

    // 인증/권한 관련 에러
    AUTHENTICATION_FAILED("AUTHENTICATION_FAILED", "인증에 실패했습니다."),
    AUTHORIZATION_FAILED("AUTHORIZATION_FAILED", "권한이 없습니다."),
    ;

    private final String code;
    private final String message;

    DomainCommonError(
            String code,
            String message
    ) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
