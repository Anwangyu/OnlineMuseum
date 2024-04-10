package com.school.onlinemuseums.server;

import com.school.onlinemuseums.domain.entity.User_basic;

import java.util.List;

public interface Student_Service{
    int student_register(User_basic userBasic);
    int student_login(User_basic userBasic);

    int deleteStudent(Long studentId);

    int updatePassword(Long studentId, String oldPassword, String newPassword);

    List<User_basic> list();
}