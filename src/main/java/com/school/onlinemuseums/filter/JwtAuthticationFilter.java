package com.school.onlinemuseums.filter;

import cn.hutool.core.util.ObjectUtil;
import com.school.onlinemuseums.domain.vo.LoginUserVO;
import com.school.onlinemuseums.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @date
 * @date
 * 该接口在请求前执行一次，获取request中的数据，其中token就在请求头里
 * 获取token，根据token从redis中获取用户信息
 */
@Component
@Slf4j
public class JwtAuthticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtAuthticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取登录用户
        LoginUserVO loginUserVO = (LoginUserVO) jwtUtils.getLoginUser(request);
        // 判断是否为null
        if(ObjectUtil.isNotNull(loginUserVO)) {
            // 鉴权，跳转的时候需要访问 /index 页面
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUserVO, null, loginUserVO.getAuthorities());
            // 将用户信息存储到SecurityContext中，SecurityContext存储到SecurityContextHolder中
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // 放行，交由后边的过滤器执行，如果没有登录，就会被登录拦截器[UsernamePasswordAuthenticationFilter]拦截到
        // /auth/sys接口就不需要任何权限
        filterChain.doFilter(request,response);
    }
}
