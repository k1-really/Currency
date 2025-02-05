package com.kireally.Currency.service;

public interface TokenService {
    Long getRemainingExpirationTime(String token);
    boolean isTokenValid(String token);
}
