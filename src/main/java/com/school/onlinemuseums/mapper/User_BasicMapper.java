package com.school.onlinemuseums.mapper;


import com.school.onlinemuseums.domain.entity.User_basic;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface User_BasicMapper {
    // 注册

    @Insert("insert into student_basic (student_username, student_id, student_password) values (#{studentUsername}, #{studentId}, #{studentPassword})")
    void register(User_basic user);

    // 登录
    @Select("select student_id as studentId, student_password as studentPassword from student_basic where student_id = #{studentId} and student_password = #{studentPassword}")
    User_basic login(Long studentId,String studentPassword);
    // 用户名验证
    @Select("select student_username as studentUsername from student_basic where student_username=#{studentUsername}")
    User_basic usernameVerification(String studentUsername);
    // id验证
    @Select("select student_id as studentId from student_basic where student_id=#{studentId}")
    User_basic idVerification(Long studentId);
    // password校验
    @Select("select student_password as studentPassword from student_basic where student_password=#{password}}")
    User_basic pswVerification(String password);
    // 删除
    @Delete("delete from student_basic where student_id = #{studentId}")
    int deleteUser(Long studentId);
    // 更新密码
    @Update("update student_basic set student_password = #{newPassword} where student_id = #{studentId}")
    int updatePassword(Long studentId, String newPassword, String password);


    // 查询所有用户
    @Select("select student_username as studentUsername,student_id as studentId, student_password as studentPassword from student_basic")
    List<User_basic> getAllUsers();


}
