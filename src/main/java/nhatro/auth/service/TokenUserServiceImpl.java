package nhatro.auth.service;

import nhatro.core.domain.entity.TokenUserEntity;

public interface TokenUserServiceImpl {
    TokenUserEntity findByToken(String token);
}
