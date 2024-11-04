package com.kireally.Currency.service.impl;

import com.kireally.Currency.service.AuthService;
import com.kireally.Currency.web.dto.auth.JwtRequest;
import com.kireally.Currency.web.dto.auth.JwtResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        return null;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }
}
