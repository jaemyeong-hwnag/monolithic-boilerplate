package com.hjm.monolithicboilerplate.domain.common.vo;

import com.hjm.monolithicboilerplate.domain.common.exception.InvalidIdException;

import lombok.Getter;

public abstract class  AbstractId<T extends AbstractId<T>> {
    @Getter
    private final Long id;

    protected AbstractId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidIdException();
        }
        this.id = id;
    }
}
