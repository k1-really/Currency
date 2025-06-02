package com.kireally.Currency.service;

import com.kireally.Currency.model.dto.auth.JwtRequest;
import com.kireally.Currency.model.dto.auth.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);
    JwtResponse refresh(String refreshToken);
    void logout(String token);
}
