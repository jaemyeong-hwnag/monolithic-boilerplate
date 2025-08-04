package com.hjm.monolithicboilerplate.app.user.facade;

import com.hjm.monolithicboilerplate.domain.user.service.UserService;
import com.hjm.monolithicboilerplate.domain.user.vo.User;
import com.hjm.monolithicboilerplate.domain.user.vo.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * User 도메인 Facade 클래스
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public User getUserById(Long id) {
        log.debug("사용자 조회: id={}", id);

        return userService.get(new UserId(id));
    }
}