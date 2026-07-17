package com.banking.customer.config;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final String SECRET =
            "mysecretkeymysecretkeymysecretkey123456";

    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + 1000 * 60 * 60))
                .signWith(
                        Keys.hmacShaKeyFor(
                                SECRET.getBytes()),
                        SignatureAlgorithm.HS256)
                .compact();
    }

//    public String extractUsername(String token) {
//
//        return Jwts.parserBuilder()
//                .setSigningKey(
//                        Keys.hmacShaKeyFor(
//                                SECRET.getBytes()))
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    public boolean validateToken(String token,
//                                 String username) {
//
//        return extractUsername(token)
//                .equals(username);
//    }
}
