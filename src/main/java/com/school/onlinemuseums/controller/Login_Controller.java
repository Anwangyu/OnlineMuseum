package com.school.onlinemuseums.controller;

import com.school.onlinemuseums.domain.entity.User_basic;
import com.school.onlinemuseums.result.Result;
import com.school.onlinemuseums.server.Student_Service;
import com.school.onlinemuseums.util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Login_Controller {

    @Autowired
    private Student_Service studentService;
    @Autowired
    private TokenGenerator tokenGenerator;

    @RequestMapping(value = "/studentReg", method = RequestMethod.POST)
    public Result student_register(@RequestBody User_basic userBasic) {
        if (studentService.student_register(userBasic) == 0) {
            return Result.error("用户名已存在");
        } else {
            return Result.ok("注册成功");
        }
    }

    // 登录校验
    @PostMapping("/studentLogin")
    public Result student_login(@RequestBody User_basic userBasic) {
        int loginResult = studentService.student_login(userBasic);
        if (loginResult == 1) {
            // 用户登录成功，生成令牌并返回给客户端
            Long studentId = userBasic.getStudentId();
            String token = tokenGenerator.generateToken(studentId);
            return Result.ok("登录成功").put("token", token);
        } else {
            // 用户登录失败，返回相应的错误信息
            return switch (loginResult) {
                case -1 -> Result.error("用户不存在");
                case -2 -> Result.error("学号错误");
                case -3 -> Result.error("密码错误");
                default -> Result.error("未知错误");
            };
        }
    }

}
