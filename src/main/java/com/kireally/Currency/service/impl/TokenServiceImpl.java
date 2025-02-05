/*
package com.kireally.Currency.service.impl;

import com.kireally.Currency.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${security.jwt.secret}")
    private String secretKey;

    // Получение ключа из секрета
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Проверка валидности токена
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false; // Токен невалиден
        }
    }

    // Получение оставшегося времени жизни токена
    public Long getRemainingExpirationTime(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration = claims.getExpiration();
            return expiration.getTime() - System.currentTimeMillis(); // Оставшееся время в мс
        } catch (JwtException e) {
            return null; // Ошибка при обработке токена
        }
    }
}*/
