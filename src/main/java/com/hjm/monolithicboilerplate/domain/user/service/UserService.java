package com.hjm.monolithicboilerplate.domain.user.service;

import com.hjm.monolithicboilerplate.common.exception.NotFoundException;
import com.hjm.monolithicboilerplate.domain.user.infrastructure.reader.UserReader;
import com.hjm.monolithicboilerplate.domain.user.vo.User;
import com.hjm.monolithicboilerplate.domain.user.vo.UserId;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * User 도메인 Service 클래스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserReader userReader;

    public User get(UserId userId) {
        Long id = userId.getId();

        log.debug("사용자 조회: id={}", id);

        User user = userReader.findById(id);

        if (user == null) {
            throw new NotFoundException(id, User.class);
        }

        return user;
    }
} 