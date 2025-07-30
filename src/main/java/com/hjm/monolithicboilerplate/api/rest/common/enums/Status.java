package com.hjm.monolithicboilerplate.api.rest.common.enums;

import com.hjm.monolithicboilerplate.domain.common.enums.EntityStatus;

public enum Status {
    /**
     * 활성 상태 (정상 사용 가능)
     */
    ACTIVE("ACTIVE", "활성"),
    /**
     * 비활성 상태 (일시적 사용 불가)
     */
    INACTIVE("INACTIVE", "비활성"),
    /**
     * 삭제된 상태 (소프트 삭제)
     */
    DELETED("DELETED", "삭제됨"),
    /**
     * 정지된 상태 (관리자 정지)
     */
    SUSPENDED("SUSPENDED", "정지됨"),
    /**
     * 대기 상태 (승인 대기)
     */
    PENDING("PENDING", "대기중");

    private final String code;
    private final String description;

    Status(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Status fromCode(String code) {
        for (Status status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean isDeleted() {
        return this == DELETED;
    }

    public boolean isUsable() {
        return this == ACTIVE || this == PENDING;
    }

    @Override
    public String toString() {
        return code;
    }

    /**
     * EntityStatus를 Status로 변환합니다.
     *
     * @param entityStatus EntityStatus
     * @return Status
     */
    public static Status convertEntityStatusToStatus(EntityStatus entityStatus) {
        switch (entityStatus) {
            case ACTIVE:
                return Status.ACTIVE;
            case INACTIVE:
                return Status.INACTIVE;
            case DELETED:
                return Status.DELETED;
            default:
                return Status.INACTIVE;
        }
    }
}
