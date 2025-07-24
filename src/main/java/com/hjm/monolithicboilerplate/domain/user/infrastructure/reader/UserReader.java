package com.hjm.monolithicboilerplate.domain.user.infrastructure.reader;

import org.springframework.stereotype.Component;

import com.hjm.monolithicboilerplate.domain.user.infrastructure.repository.UserRepository;
import com.hjm.monolithicboilerplate.domain.user.vo.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * UserReader 인터페이스 구현체
 * 
 * 사용자 엔티티의 읽기 전용 작업을 구현하는 클래스입니다.
 * Repository를 사용하여 데이터 조회 작업을 수행합니다.
 * 
 * @author HJM
 * @version 1.0
 * @since 2024-01-01
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserReader {
    
    private final UserRepository userRepository;
    
    public User findById(Long id) {
        log.debug("사용자 조회: id={}", id);
        return userRepository.findById(id).orElse(null);
    }
} 