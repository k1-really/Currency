package com.kireally.Currency.service.impl;

import com.kireally.Currency.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class TokenBlackListServiceImpl implements TokenBlacklistService {
    private final RedisTemplate<String, String> redisTemplate;

    private static final String BLACKLIST_PREFIX = "blacklisted:";

    @Override
    public void blacklistToken(String token, long expirationMillis) {
        String key = BLACKLIST_PREFIX + token;
        redisTemplate.opsForValue().set(key, "true", expirationMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        String key = BLACKLIST_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
