package com.school.onlinemuseums.server.impl;

import com.school.onlinemuseums.domain.entity.User_basic;
import com.school.onlinemuseums.mapper.User_BasicMapper;
import com.school.onlinemuseums.server.Student_Service;
import com.school.onlinemuseums.util.RedisCacheManager;
import com.school.onlinemuseums.util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class Student_ServiceImpl implements Student_Service {

    @Autowired
    private User_BasicMapper userBasicMapper;
    @Autowired
    private TokenGenerator tokenGenerator; // 注入令牌生成器
    @Autowired
    private RedisCacheManager redisCacheManager;

    @Override
    public int student_register(User_basic userBasic) {
        if(userBasicMapper.usernameVerification(userBasic.getStudentUsername()) != null){
            System.out.println("用户已存在");
            return 0;
        }else{
            System.out.println("注册成功");
            userBasicMapper.register(userBasic);
            return 1;
        }
    }

    @Override
    public int student_login(User_basic userBasic) {
        User_basic nameUser = userBasicMapper.usernameVerification(userBasic.getStudentUsername());
        if (nameUser == null) {
            System.out.println("用户不存在");
            return -1;
        }
        User_basic idUser = userBasicMapper.idVerification(userBasic.getStudentId());
        if (idUser == null) {
            System.out.println("学号不存在");
            return -2;
        }
        User_basic existingUser = userBasicMapper.login(userBasic.getStudentId(), userBasic.getStudentPassword());
        if (existingUser != null) {
            if (existingUser.getStudentId() != null && existingUser.getStudentId().equals(userBasic.getStudentId())) {
                if (existingUser.getStudentPassword() != null && existingUser.getStudentPassword().equals(userBasic.getStudentPassword())) {
                    System.out.println("登录成功");
                    String token = tokenGenerator.generateToken(existingUser.getStudentId()); // 生成令牌
                    return 1;
                } else {
                    System.out.println("密码错误");
                    return -3;
                }
            } else {
                System.out.println("未知错误");
                return 0;
            }
        } else {
            System.out.println("密码错误");
            return -3;
        }
    }

    @Override
    public int deleteStudent(Long studentId) {
        // 删除学生信息，同时删除缓存中的数据
        int rowsAffected = userBasicMapper.deleteUser(studentId);
        if (rowsAffected > 0) {
            String cacheKey = "student:" + studentId;
            redisCacheManager.deleteCache(cacheKey); // 删除缓存
        }
        return rowsAffected;
    }

    @Override
    public int updatePassword(Long studentId, String oldPassword, String newPassword) {
        // 更新密码，同时更新缓存中的数据
        User_basic existingUser = userBasicMapper.login(studentId, oldPassword);
        if (existingUser == null) {
            System.out.println("旧密码匹配错误");
            return -1;
        }
        int rowsAffected = userBasicMapper.updatePassword(studentId, newPassword, newPassword);
        if (rowsAffected > 0) {
            System.out.println("更新成功");
            String cacheKey = "student:" + studentId;
            User_basic updatedUser = userBasicMapper.idVerification(studentId);
            redisCacheManager.setCache(cacheKey, updatedUser, 30, TimeUnit.MINUTES); // 更新缓存
            return 1;
        } else {
            System.out.println("更新失败");
            return 0;
        }
    }

    @Override
    public List<User_basic> list() {
        return userBasicMapper.getAllUsers();
    }
}
