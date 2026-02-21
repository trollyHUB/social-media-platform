package com.tolegen.webapplicationdevelopmentlab2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурация безопасности Spring Security
 * Отключает CSP ограничения и разрешает все запросы без авторизации
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Отключаем CSRF (не нужно для REST API)
            .csrf(csrf -> csrf.disable())
            // Разрешаем все запросы без авторизации
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            // Отключаем заголовки по умолчанию (в том числе CSP)
            .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives(
                        "default-src 'self'; " +
                        "script-src 'self' 'unsafe-inline' 'unsafe-eval'; " +
                        "style-src 'self' 'unsafe-inline'; " +
                        "img-src 'self' data:; " +
                        "font-src 'self'; " +
                        "connect-src 'self'"
                    )
                )
            );

        return http.build();
    }
}
