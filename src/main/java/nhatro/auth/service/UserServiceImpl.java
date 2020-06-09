package nhatro.auth.service;

import nhatro.auth.domain.request.LoginRequest;
import nhatro.core.domain.entity.UserEntity;

public interface UserServiceImpl {
    UserEntity findUserById(Integer id);

    UserEntity login(LoginRequest request);
}
