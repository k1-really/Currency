package com.kireally.Currency.service.impl;

import com.kireally.Currency.model.entity.user.User;
import com.kireally.Currency.service.AuthService;
import com.kireally.Currency.service.TokenBlacklistService;
import com.kireally.Currency.service.UserService;
import com.kireally.Currency.model.dto.auth.JwtRequest;
import com.kireally.Currency.model.dto.auth.JwtResponse;
import com.kireally.Currency.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService tokenBlacklistService;


    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword()));
        User user = userService.getByEmail(loginRequest.getEmail());
        jwtResponse.setId(user.getId());
        jwtResponse.setEmail(user.getEmail());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(),user.getEmail(),user.getRoles()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getId(),user.getEmail()));
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserToken(refreshToken);
    }

    @Override
    public void logout(String token) {
        // Обрезаем "Bearer " если он есть
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Извлекаем срок действия токена
        Date expiration = jwtTokenProvider.getExpirationDate(token);
        long ttl = expiration.getTime() - System.currentTimeMillis();

        // Добавляем токен в Redis blacklist
        if (ttl > 0) {
            tokenBlacklistService.blacklistToken(token, ttl);
        }
    }
}
