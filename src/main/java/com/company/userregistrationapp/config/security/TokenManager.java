package com.company.userregistrationapp.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenManager {

    @Value("${spring.security.accessToken.time}")
    private Long accessExpirationTime;

    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username) {

        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + accessExpirationTime);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public boolean tokenValidate(String token) {

        return getUserFromToken(token) != null && isExpired(token);
    }

    public String getUserFromToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isExpired(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date(System.currentTimeMillis()));
    }
}
