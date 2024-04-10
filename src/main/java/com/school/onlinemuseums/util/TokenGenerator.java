package com.school.onlinemuseums.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class TokenGenerator {

    private static final String SECRET_KEY = "123123";

    public static String generateToken(Long studentId) {
        long expirationTime = 3600000;
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        // 生成令牌
        return Jwts.builder()
                .setSubject(Long.toString(studentId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
