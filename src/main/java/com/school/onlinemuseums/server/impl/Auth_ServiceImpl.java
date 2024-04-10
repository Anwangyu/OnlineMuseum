//package com.school.onlinemuseums.server.impl;
//
//import cn.hutool.core.util.ObjectUtil;
//import com.school.onlinemuseums.common.ServiceException;
//import com.school.onlinemuseums.domain.dto.LoginDto;
//import com.school.onlinemuseums.server.IAuth_Service;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.stereotype.Service;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//@Service
//@Slf4j
//public class Auth_ServiceImpl implements IAuth_Service {
//    private final AuthenticationManager authenticationManager;
//    private final JwtUtils jwtUtils;
//
//    public Auth_ServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
//        this.authenticationManager = authenticationManager;
//        this.jwtUtils = jwtUtils;
//    }
//    /**
//     * login方法
//     */
//    @Override
//    public String login(LoginDto loginDto) {
//        UsernamePasswordAuthenticationToken authentication =
//                new UsernamePasswordAuthenticationToken(loginDto.getAccount(), loginDto.getPassword());
//        // 调用loaduserbyusername方法
//        Authentication authenticate = authenticationManager.authenticate(authentication);
//        // 获取用户信息，返回的就是UserDetails
//        LoginUserVO loginUser = (LoginUserVO) authenticate.getPrincipal();
//        // 根据loginUser创建token
//        if(ObjectUtil.isNull(loginUser)) {
//            throw new ServiceException(HttpStatus.UNAUTHORIZED,"认证失败！");
//        }
//        // 创建token,此处的token时由UUID编码而成JWT字符串
//        String token = jwtUtils.createToken(loginUser);
//        log.info("token===》{}",token);
//        return token;
//    }
//}
