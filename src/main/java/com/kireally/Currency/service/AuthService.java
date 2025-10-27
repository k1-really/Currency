package com.kireally.Currency.service;

import com.kireally.Currency.model.auth.JwtRequest;
import com.kireally.Currency.model.auth.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);
    JwtResponse refresh(String refreshToken);
    void logout(String token);
}
