package com.school.onlinemuseums;

import com.school.onlinemuseums.domain.entity.User_basic;
import com.school.onlinemuseums.domain.vo.LoginUserVO;
import com.school.onlinemuseums.util.JwtUtils;
import com.school.onlinemuseums.util.RedisCacheUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JwtUtilsTest {

    @Autowired
    private JwtUtils jwtUtils;

    @MockBean
    private RedisCacheUtil redisCacheUtil;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    @Test
    public void testCreateAndParseToken() {
        // 创建一个模拟的用户对象
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setStudentUsername("testUser");

        // 模拟 Redis 缓存
        when(redisCacheUtil.getCacheObject("123123")).thenReturn(loginUserVO);

        // 生成 Token
        String token = jwtUtils.createToken(loginUserVO);

        // 解析 Token
        Claims claims = jwtUtils.parseToken(token);
        String username = claims.getSubject();

        // 验证生成和解析后的用户名是否一致
        assertEquals(loginUserVO.getUsername(), username);

        // 验证 Token 过期时间是否正确
        long expirationTime = claims.getExpiration().getTime();
        long expectedExpiration = System.currentTimeMillis() + expiration * 1000;
        long tolerance = 1000; // 允许的时间误差，单位为毫秒
        assertTrue(expirationTime >= expectedExpiration - tolerance && expirationTime <= expectedExpiration + tolerance);
    }

}


