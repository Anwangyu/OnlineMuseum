package com.school.onlinemuseums.domain.vo;

import cn.hutool.core.util.ObjectUtil;
import com.school.onlinemuseums.domain.entity.User_basic;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class LoginUserVO implements UserDetails {
    private Long id;

    private String token;

    private long loginTime;
    private Long studentId;
    private String studentUsername;
    private String studentPassword;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }


    // 用户信息
    private User_basic userBasic = new User_basic();

    /**
     * 用户的权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> perms = userBasic.getPerms();
        // 判空，返回数据
        if(ObjectUtil.isNotEmpty(perms)) {
            return perms.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public String getPassword() {
        return userBasic.getStudentPassword();
    }

    @Override
    public String getUsername() {
        return userBasic.getStudentUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userBasic.getStatus() == 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return userBasic.getStatus() == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userBasic.getStatus() == 0;
    }

    @Override
    public boolean isEnabled() {
        return userBasic.getStatus() == 0;
    }



}
