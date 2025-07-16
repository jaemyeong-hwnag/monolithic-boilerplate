package com.hjm.monolithicboilerplate.domain.user.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hjm.monolithicboilerplate.domain.user.entity.UserEntity;

/**
 * User 도메인 Repository 인터페이스
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    /**
     * 이메일로 사용자를 조회합니다.
     * 
     * @param email 조회할 사용자의 이메일
     * @return 사용자 Optional 객체
     */
    Optional<UserEntity> findByEmail(String email);
} 