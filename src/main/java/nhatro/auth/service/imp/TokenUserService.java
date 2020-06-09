package nhatro.auth.service.imp;

import nhatro.auth.repo.TokenUserRepo;
import nhatro.auth.service.TokenUserServiceImpl;
import nhatro.core.domain.entity.TokenUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenUserService implements TokenUserServiceImpl {
    @Autowired
    TokenUserRepo tokenUserRepo;

    @Override
    public TokenUserEntity findByToken(String token) {
        return null;
    }
}
