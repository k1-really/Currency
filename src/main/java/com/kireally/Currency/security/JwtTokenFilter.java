package com.kireally.Currency.security;

import com.kireally.Currency.exception.ResourceNotFoundException;
import com.kireally.Currency.service.TokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService tokenBlacklistService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String bearerToken = ((HttpServletRequest) servletRequest).getHeader("Authorization");

        // Проверка на наличие токена в запросе
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7); // Убираем префикс "Bearer " из токена
        }

        if (bearerToken != null && jwtTokenProvider.validateToken(bearerToken)) {
            // Проверяем, не в чёрном ли списке этот токен
            if (tokenBlacklistService.isTokenBlacklisted(bearerToken)) {
                // Если токен в чёрном списке — возвращаем ошибку
                HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse; // Приводим к HttpServletResponse
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Устанавливаем статус 401 Unauthorized
                return; // Прерываем выполнение фильтра
            }

            try {
                // Если токен действителен, получаем аутентификацию
                Authentication auth = jwtTokenProvider.getAuthentication(bearerToken);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth); // Устанавливаем аутентификацию в контекст безопасности
                }
            } catch (ResourceNotFoundException ignored) {
                // В случае ошибки мы не устанавливаем аутентификацию, просто игнорируем
            }
        }

        filterChain.doFilter(servletRequest, servletResponse); // Продолжаем обработку запроса
    }

}
