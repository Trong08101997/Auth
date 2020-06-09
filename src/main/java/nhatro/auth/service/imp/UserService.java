package nhatro.auth.service.imp;

import nhatro.auth.common.AuthJwt;
import nhatro.auth.domain.request.LoginRequest;
import nhatro.auth.repo.UserRepo;
import nhatro.auth.service.UserServiceImpl;
import nhatro.core.domain.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceImpl {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserEntity findUserById(Integer id) {
        return null;
    }

    @Override
    public UserEntity login(LoginRequest request) {
        String hashKey = AuthJwt.get_SHA_256_SecurePassword(request.getPassword());
        return userRepo.findTopByUsernameAndAndHashkey(request.getUsername(), hashKey);
    }
}
