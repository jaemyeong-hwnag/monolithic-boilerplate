package com.hjm.monolithicboilerplate.domain.common.exception;

import com.hjm.monolithicboilerplate.common.exception.BusinessException;
import com.hjm.monolithicboilerplate.common.exception.ErrorCode;

import static com.hjm.monolithicboilerplate.domain.common.exception.DomainCommonError.INVALID_ID;

public class InvalidIdException extends BusinessException {
    public InvalidIdException() {
        super(INVALID_ID);
    }

    public InvalidIdException(
            ErrorCode error,
            Throwable cause
    ) {
        super(error, cause);
    }
}
