package com.school.onlinemuseums.controller;

import com.school.onlinemuseums.domain.entity.User_basic;
import com.school.onlinemuseums.server.Student_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class Student_Controller {

    @Autowired
    private Student_Service studentService;

    @DeleteMapping("/students/{studentId}")
    public String deleteStudent(@PathVariable Long studentId) {
        int rowsAffected = studentService.deleteStudent(studentId);
        if (rowsAffected > 0) {
            return "StudentID " + studentId + "删除成功";
        } else {
            return "用户删除失败" + studentId;
        }
    }

    @PutMapping("/students/{studentId}/password")
    public ResponseEntity<String> updatePassword(@PathVariable Long studentId, @RequestBody Map<String, String> passwordMap){
        String oldPassword = passwordMap.get("oldPassword");
        String newPassword = passwordMap.get("newPassword");

        int result = studentService.updatePassword(studentId, oldPassword, newPassword);
        if (result == 1){
            System.out.println("1");
            return ResponseEntity.ok("密码更新成功");
        } else if (result == 0){
            return ResponseEntity.badRequest().body("更新错误");
        } else {
            return ResponseEntity.badRequest().body("原密码错误");
        }
    }



    @GetMapping("/search")
    public ResponseEntity<?> getAllUsers() {
        List<User_basic> userList = studentService.list();
        if (userList != null) {
            return ResponseEntity.ok().body(userList);
        } else {
            System.out.println();
            return ResponseEntity.notFound().build();
        }
    }

}
