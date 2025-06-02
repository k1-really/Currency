package com.kireally.Currency.service;

public interface TokenBlacklistService {
    void blacklistToken(String token, long expirationMillis);
    boolean isTokenBlacklisted(String token);
}
