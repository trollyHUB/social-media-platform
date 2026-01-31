package com.tolegen.webapplicationdevelopmentlab2.security;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Фильтр безопасности для всех запросов
 * Security filter for all requests
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("🔒 Security Filter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 1. Добавляем заголовки безопасности
        addSecurityHeaders(httpResponse);

        // 2. Проверка rate limiting (защита от DDoS)
        String clientIp = httpRequest.getRemoteAddr();
        if (!SecurityUtil.RateLimiter.checkRateLimit(clientIp, 100, 60000)) {
            httpResponse.setStatus(429); // Too Many Requests
            httpResponse.getWriter().write("⚠️ Слишком много запросов. Попробуйте позже.");
            return;
        }

        // 3. Логирование подозрительных запросов
        String uri = httpRequest.getRequestURI();
        if (isSuspiciousRequest(uri)) {
            System.err.println("⚠️ Подозрительный запрос: " + uri + " от IP: " + clientIp);
        }

        // 4. Продолжаем цепочку фильтров
        chain.doFilter(request, response);
    }

    /**
     * Добавление заголовков безопасности
     */
    private void addSecurityHeaders(HttpServletResponse response) {
        // Защита от XSS
        response.setHeader("X-XSS-Protection", "1; mode=block");

        // Защита от clickjacking
        response.setHeader("X-Frame-Options", "DENY");

        // Запрет определения типа контента браузером
        response.setHeader("X-Content-Type-Options", "nosniff");

        // Content Security Policy (защита от XSS и инъекций)
        response.setHeader("Content-Security-Policy",
            "default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'");

        // Referrer Policy
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");

        // Принудительное использование HTTPS (если используется)
        // response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
    }

    /**
     * Проверка на подозрительные запросы
     */
    private boolean isSuspiciousRequest(String uri) {
        if (uri == null) return false;

        String lowerUri = uri.toLowerCase();

        // Проверяем на попытки эксплуатации
        return lowerUri.contains("../") ||           // Path traversal
               lowerUri.contains("..\\") ||          // Path traversal Windows
               lowerUri.contains("%2e%2e") ||        // Encoded path traversal
               lowerUri.contains("script") ||        // XSS попытки
               lowerUri.contains("exec") ||          // Command injection
               lowerUri.contains("eval") ||          // Code injection
               lowerUri.contains("base64") ||        // Encoded attacks
               lowerUri.contains("system") ||        // System commands
               lowerUri.contains("/etc/passwd") ||   // Linux exploits
               lowerUri.contains("cmd.exe");         // Windows exploits
    }

    @Override
    public void destroy() {
        System.out.println("🔒 Security Filter destroyed");
        // Очистка rate limiter
        SecurityUtil.RateLimiter.cleanup();
    }
}
