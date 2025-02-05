package com.kireally.Currency.service.impl;

import com.kireally.Currency.model.user.User;
import com.kireally.Currency.service.AuthService;
import com.kireally.Currency.service.UserService;
import com.kireally.Currency.web.dto.auth.JwtRequest;
import com.kireally.Currency.web.dto.auth.JwtResponse;
import com.kireally.Currency.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


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
}
