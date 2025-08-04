package com.hjm.monolithicboilerplate.common.exception;

public class NotFoundException extends BaseException {
    private static final String errorMessage = "%s 를 찾지 못했습니다. id: %d";

    public NotFoundException(Long id, Class<?> clazz) {
        super(String.format(errorMessage, clazz.getSimpleName(), id));
    }
}
