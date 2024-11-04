package com.kireally.Currency.service;

import com.kireally.Currency.web.dto.auth.JwtRequest;
import com.kireally.Currency.web.dto.auth.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);
    JwtResponse refresh(String refreshToken);
}
