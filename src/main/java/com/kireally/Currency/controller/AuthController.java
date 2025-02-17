package com.kireally.Currency.controller;

import com.kireally.Currency.mapper.UserMapper;
import com.kireally.Currency.model.user.User;
import com.kireally.Currency.repository.UserRepository;
import com.kireally.Currency.service.AuthService;
import com.kireally.Currency.service.UserService;
import com.kireally.Currency.web.dto.auth.JwtRequest;
import com.kireally.Currency.web.dto.auth.JwtResponse;
import com.kireally.Currency.web.dto.user.UserDto;
import com.kireally.Currency.web.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/currency/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    //private final TokenService tokenService;
   // private final RedisTemplate<String, String> redisTemplate;



    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDto register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);
        return userMapper.toDto(createdUser);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // Получаем UserDetails из контекста безопасности

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = userDetails.getUsername();


        return ResponseEntity.ok(userMapper.toDto(userRepository.findByEmail(email).orElse(null)));
    }

 /*   @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        // Проверка, что заголовок Authorization передан
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization header");
        }

        // Извлечение токена из заголовка
        String token = authorizationHeader.substring(7); // Убираем "Bearer "

        // Проверяем, валиден ли токен
        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        // Добавляем токен в Redis с TTL равным оставшемуся времени жизни токена
        Long remainingTime = tokenService.getRemainingExpirationTime(token);
        if (remainingTime != null) {
            redisTemplate.opsForValue().set(token, "blacklisted", remainingTime, TimeUnit.MILLISECONDS);
        }

        return ResponseEntity.ok("Logged out successfully");
    }*/
}
