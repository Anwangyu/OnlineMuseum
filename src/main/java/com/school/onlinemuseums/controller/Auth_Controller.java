package com.school.onlinemuseums.controller;

import com.school.onlinemuseums.domain.dto.LoginDto;
import com.school.onlinemuseums.server.IAuth_Service;
import com.school.onlinemuseums.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * 认证接口
 */
@RestController
@RequestMapping("auth")
@Slf4j
//@CrossOrigin
public class Auth_Controller {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final IAuth_Service authService;


    public Auth_Controller(IAuth_Service authService) {
        this.authService = authService;
    }

    /**
     * 系统用户登陆
     * @return
     */
    @PostMapping("/students")
    public Result studentLogin(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        return Result.success("操作成功").put("token",token);
    }

}
