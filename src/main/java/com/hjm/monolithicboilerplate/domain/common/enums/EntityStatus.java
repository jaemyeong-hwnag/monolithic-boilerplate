package com.hjm.monolithicboilerplate.domain.common.enums;

/**
 * 엔티티 상태 열거형
 *
 * 모든 도메인 엔티티가 공통으로 사용하는 상태를 정의합니다.
 * 활성, 비활성, 삭제, 정지, 대기 상태를 관리합니다.
 */
public enum EntityStatus {
    /** 활성 상태 (정상 사용 가능) */
    ACTIVE("ACTIVE", "활성"),
    /** 비활성 상태 (일시적 사용 불가) */
    INACTIVE("INACTIVE", "비활성"),
    /** 삭제된 상태 (소프트 삭제) */
    DELETED("DELETED", "삭제됨"),
    /** 정지된 상태 (관리자 정지) */
    SUSPENDED("SUSPENDED", "정지됨"),
    /** 대기 상태 (승인 대기) */
    PENDING("PENDING", "대기중");

    private final String code;
    private final String description;

    EntityStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() { return code; }
    public String getDescription() { return description; }

    public static EntityStatus fromCode(String code) {
        for (EntityStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }

    public boolean isActive() { return this == ACTIVE; }
    public boolean isDeleted() { return this == DELETED; }
    public boolean isUsable() { return this == ACTIVE || this == PENDING; }

    @Override
    public String toString() { return code; }
} 