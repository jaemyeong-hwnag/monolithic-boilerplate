package com.hjm.monolithicboilerplate.domain.domain.entity;

import com.hjm.monolithicboilerplate.domain.common.entity.BaseEntity;
import com.hjm.monolithicboilerplate.domain.common.enums.EntityStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BaseEntity 테스트
 * <p>
 * BaseEntity의 공통 기능(상태, 시간, 감사 필드 등)을 검증합니다.
 */
@DisplayName("BaseEntity 테스트")
class BaseEntityTest {
    static class TestEntity extends BaseEntity<Long> {
        private String value;

        public TestEntity() {
            super();
        }

        public TestEntity(Long id) {
            super(id);
        }

        public void setValue(String value) {
            this.value = value;
            update();
        }

        public String getValue() {
            return value;
        }
    }

    @Test
    @DisplayName("기본 생성자 동작 테스트")
    void testDefaultConstructor() {
        TestEntity entity = new TestEntity();
        assertNull(entity.getId());
        assertEquals(EntityStatus.ACTIVE, entity.getStatus());
        assertNotNull(entity.getCreatedAt());
        assertNotNull(entity.getUpdatedAt());
        assertNull(entity.getDeletedAt());
        assertNull(entity.getCreatedBy());
        assertNull(entity.getUpdatedBy());
        assertTrue(entity.isActive());
        assertFalse(entity.isDeleted());
    }

    @Test
    @DisplayName("ID 생성자 동작 테스트")
    void testConstructorWithId() {
        TestEntity entity = new TestEntity(1L);
        assertEquals(1L, entity.getId());
        assertEquals(EntityStatus.ACTIVE, entity.getStatus());
        assertTrue(entity.isActive());
    }

    @Test
    @DisplayName("상태 변경 및 삭제 테스트")
    void testStatusAndDelete() {
        TestEntity entity = new TestEntity();
        entity.deactivate();
        assertEquals(EntityStatus.INACTIVE, entity.getStatus());
        entity.activate();
        assertEquals(EntityStatus.ACTIVE, entity.getStatus());
        entity.softDelete("admin");
        assertEquals(EntityStatus.DELETED, entity.getStatus());
        assertNotNull(entity.getDeletedAt());
        assertEquals("admin", entity.getUpdatedBy());
        assertTrue(entity.isDeleted());
        entity.hardDelete();
        assertEquals(EntityStatus.DELETED, entity.getStatus());
        assertTrue(entity.isDeleted());
    }

    @Test
    @DisplayName("수정자 및 시간 업데이트 테스트")
    void testUpdate() throws InterruptedException {
        TestEntity entity = new TestEntity();
        LocalDateTime before = entity.getUpdatedAt();
        Thread.sleep(5);
        entity.setValue("test");
        assertTrue(entity.getUpdatedAt().isAfter(before));
        entity.update("user");
        assertEquals("user", entity.getUpdatedBy());
    }

    @Test
    @DisplayName("equals/hashCode/toString 테스트")
    void testEqualsHashCodeToString() {
        TestEntity e1 = new TestEntity(1L);
        TestEntity e2 = new TestEntity(1L);
        TestEntity e3 = new TestEntity(2L);
        assertEquals(e1, e2);
        assertNotEquals(e1, e3);
        assertEquals(e1.hashCode(), e2.hashCode());
        assertTrue(e1.toString().contains("TestEntity"));
        assertTrue(e1.toString().contains("id=1"));
    }
} 