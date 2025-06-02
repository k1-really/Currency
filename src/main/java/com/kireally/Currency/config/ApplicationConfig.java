package com.kireally.Currency.config;

import com.kireally.Currency.service.TokenBlacklistService;
import com.kireally.Currency.security.JwtTokenFilter;
import com.kireally.Currency.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Lazy
@RequiredArgsConstructor(onConstructor_ = @__(@Lazy))// In order to rid of cycle dependencies
public class ApplicationConfig {

    private final JwtTokenProvider tokenProvider;
    private final ApplicationContext context;
    private final TokenBlacklistService tokenBlacklistService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(tokenProvider, tokenBlacklistService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Отключаем CSRF-защиту, так как приложение без состояния
                .csrf(AbstractHttpConfigurer::disable)

                // Настраиваем CORS (можно задать дополнительные настройки)
                .cors(Customizer.withDefaults())

                // Отключаем базовую HTTP-аутентификацию, используем JWT
                .httpBasic(AbstractHttpConfigurer::disable)

                // Устанавливаем стратегию управления сессиями как "безсессионная" (статус STATELESS)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Настраиваем обработчики исключений: если пользователь неавторизован или доступ запрещён
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.getWriter().write("Unauthorized.");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.getWriter().write("Forbidden.");
                        })
                )

                // Настраиваем доступ к конечным точкам (авторизация запросов)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/currency/auth/**").permitAll() // Разрешаем доступ к эндпоинтам авторизации без авторизации
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()                    // Остальные запросы требуют аутентификации
                )

                // Отключаем возможность использования анонимного пользователя
                .anonymous(AbstractHttpConfigurer::disable)

                // Добавляем JWT-фильтр перед фильтром UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)

                // Завершаем конфигурацию
                .build();
    }
}
