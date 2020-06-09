package nhatro.auth.controller;

import nhatro.auth.common.AuthJwt;
import nhatro.auth.common.JwtData;
import nhatro.auth.domain.request.LoginRequest;
import nhatro.auth.service.UserServiceImpl;
import nhatro.core.domain.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserServiceImpl userService;


    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        UserEntity userEntity = userService.login(request);
        JwtData jwtData = new JwtData();
        jwtData.setUserId(userEntity.getId());
        jwtData.setUserName(userEntity.getUsername());
        AuthJwt authJwt = new AuthJwt();
        return authJwt.createToken(jwtData);
    }
}
