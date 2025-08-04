package com.hjm.monolithicboilerplate.domain.common.entity;

import com.hjm.monolithicboilerplate.domain.common.enums.EntityStatus;

import java.time.LocalDateTime;

/**
 * 모든 도메인 엔티티의 기본 클래스
 * <p>
 * - ID, 상태, 생성/수정/삭제 시간, 감사 필드 포함
 * - 일관된 엔티티 생명주기 및 감사 추적 제공
 *
 * @param <ID> 엔티티의 ID 타입
 */
public abstract class BaseEntity<ID> {
    /**
     * 엔티티 ID
     */
    private ID id;
    /**
     * 엔티티 상태
     */
    private EntityStatus status;
    /**
     * 생성 시간
     */
    private LocalDateTime createdAt;
    /**
     * 수정 시간
     */
    private LocalDateTime updatedAt;
    /**
     * 삭제 시간 (소프트 삭제용)
     */
    private LocalDateTime deletedAt;
    /**
     * 생성자 ID (감사 추적용)
     */
    private String createdBy;
    /**
     * 수정자 ID (감사 추적용)
     */
    private String updatedBy;

    protected BaseEntity() {
        this.status = EntityStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    protected BaseEntity(ID id) {
        this();
        this.id = id;
    }

    protected BaseEntity(ID id, EntityStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, String createdBy, String updatedBy) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public boolean isActive() {
        return this.status == EntityStatus.ACTIVE;
    }

    public boolean isDeleted() {
        return this.status == EntityStatus.DELETED || this.deletedAt != null;
    }

    public void activate() {
        this.status = EntityStatus.ACTIVE;
        this.deletedAt = null;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.status = EntityStatus.INACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void softDelete(String deletedBy) {
        this.status = EntityStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = deletedBy;
    }

    public void hardDelete() {
        this.status = EntityStatus.DELETED;
        this.deletedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String updatedBy) {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = updatedBy;
    }

    public void update() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setId(ID id) {
        this.id = id;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ID getId() {
        return id;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BaseEntity<?> that = (BaseEntity<?>) obj;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id=" + id +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 