package com.school.onlinemuseums.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class TokenGenerator {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long expirationTime;

    public String generateToken(Long studentId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime * 1000);

        // 生成令牌
        return Jwts.builder()
                .setSubject(Long.toString(studentId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
