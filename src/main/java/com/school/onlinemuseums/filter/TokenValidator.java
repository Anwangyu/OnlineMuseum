//package com.school.onlinemuseums.filter;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TokenValidator {
//
//    private static final String SECRET_KEY = "123123";
//    public boolean validateToken(String token) {
//        try {
//            // 解析令牌并验证签名
//            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}
