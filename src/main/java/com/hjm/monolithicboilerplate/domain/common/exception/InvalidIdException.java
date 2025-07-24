package com.hjm.monolithicboilerplate.domain.common.exception;

import static com.hjm.monolithicboilerplate.domain.common.exception.DomainCommonError.*;

import com.hjm.monolithicboilerplate.common.exception.ErrorCode;
import com.hjm.monolithicboilerplate.common.exception.BusinessException;

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
