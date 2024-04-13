package com.school.onlinemuseums.util;

import cn.hutool.core.util.StrUtil;
import com.school.onlinemuseums.common.CacheConstants;
import com.school.onlinemuseums.domain.vo.LoginUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    public String createToken(LoginUserVO loginUserVO) {
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        loginUserVO.setToken(token);
        loginUserVO.setLoginTime(System.currentTimeMillis());
        refreshToken(loginUserVO);
        return Jwts.builder()
                .setSubject(loginUserVO.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public LoginUserVO getLoginUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StrUtil.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                String username = claims.getSubject();
                String tokenFromClaims = claims.getId();
                LoginUserVO loginUserVO = redisCacheUtil.getCacheObject(CacheConstants.LOGIN_USER_KEY + tokenFromClaims);
                if (loginUserVO != null && username.equals(loginUserVO.getUsername())) {
                    long loginTime = loginUserVO.getLoginTime();
                    long currentTimeMillis = System.currentTimeMillis();
                    long millis = (currentTimeMillis - loginTime) / 1000 / 60;
                    if (millis >= 20) {
                        refreshToken(loginUserVO);
                    }
                    return loginUserVO;
                }
            } catch (Exception e) {
                // Token 解析失败，可以进行异常处理或记录日志
            }
        }
        return null;
    }

    private void refreshToken(LoginUserVO loginUserVO) {
        redisCacheUtil.setCacheObject(CacheConstants.LOGIN_USER_KEY + loginUserVO.getToken(), loginUserVO, expiration, TimeUnit.SECONDS);
    }

}
