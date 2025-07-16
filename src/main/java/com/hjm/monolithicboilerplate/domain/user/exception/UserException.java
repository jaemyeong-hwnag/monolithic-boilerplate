package com.hjm.monolithicboilerplate.domain.user.exception;

import com.hjm.monolithicboilerplate.common.exception.BusinessException;
import com.hjm.monolithicboilerplate.common.exception.ErrorCode;

public class UserException extends BusinessException {
    public UserException(ErrorCode error) {
        super(error);
    }
}
