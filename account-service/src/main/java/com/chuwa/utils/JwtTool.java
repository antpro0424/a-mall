package com.chuwa.utils;

import com.chuwa.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtTool {

    private final KeyPair keyPair;

    @Autowired
    public JwtTool(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    // 生成JWT Token
    public String generateToken(String username, Duration ttl) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ttl.toMillis());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
                .compact();
    }

    // 解析JWT Token
    public String parseToken(String token) {
        // 1. 校验token是否为空
        if (token == null) {
            throw new UnauthorizedException("User did not login.");
        }

        // 2. 校验token是否有效
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(keyPair.getPublic())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (Exception e) {
            throw new UnauthorizedException("Token is not valid.");
        }


        return claims.getSubject();
    }
}
