package com.tolegen.webapplicationdevelopmentlab2.security;

import java.util.regex.Pattern;

/**
 * Класс для обеспечения безопасности приложения
 * Security utilities for the application
 */
public class SecurityUtil {

    // Паттерны для валидации
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Pattern USERNAME_PATTERN = Pattern.compile(
        "^[a-zA-Zа-яА-ЯёЁ0-9_]{3,30}$"
    );

    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
        ".*('|(--)|;|/\\*|\\*/|xp_|sp_|exec|execute|select|insert|update|delete|drop|create|alter).*",
        Pattern.CASE_INSENSITIVE
    );

    /**
     * Защита от XSS атак - экранирование HTML символов
     */
    public static String escapeHtml(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;")
                .replace("/", "&#x2F;");
    }

    /**
     * Защита от JavaScript инъекций
     */
    public static String sanitizeInput(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        // Удаляем потенциально опасные символы и теги
        return input
                .replaceAll("<script[^>]*>.*?</script>", "")
                .replaceAll("<iframe[^>]*>.*?</iframe>", "")
                .replaceAll("javascript:", "")
                .replaceAll("on\\w+\\s*=", "")
                .trim();
    }

    /**
     * Валидация email адреса
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Валидация username
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * Проверка на SQL инъекции
     */
    public static boolean isSqlInjection(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        return SQL_INJECTION_PATTERN.matcher(input).find();
    }

    /**
     * Валидация длины строки
     */
    public static boolean isValidLength(String text, int minLength, int maxLength) {
        if (text == null) {
            return false;
        }
        int length = text.trim().length();
        return length >= minLength && length <= maxLength;
    }

    /**
     * Очистка и валидация контента поста
     */
    public static String sanitizePostContent(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        // Удаляем лишние пробелы и переносы
        content = content.trim().replaceAll("\\s+", " ");
        // Проверяем на SQL инъекции
        if (isSqlInjection(content)) {
            throw new SecurityException("Обнаружена попытка SQL инъекции!");
        }
        // Санитизация
        content = sanitizeInput(content);
        return content;
    }

    /**
     * Проверка на безопасность параметров URL
     */
    public static boolean isValidUrlParameter(String param) {
        if (param == null || param.isEmpty()) {
            return false;
        }
        // Проверяем что параметр содержит только безопасные символы
        return param.matches("^[a-zA-Zа-яА-ЯёЁ0-9_-]+$");
    }

    /**
     * Генерация безопасного ID (для предотвращения IDOR атак)
     */
    public static String generateSecureId(int id) {
        // Простое шифрование ID
        return Integer.toHexString(id * 37 + 17);
    }

    /**
     * Расшифровка безопасного ID
     */
    public static int decodeSecureId(String encodedId) {
        try {
            int value = Integer.parseInt(encodedId, 16);
            return (value - 17) / 37;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Проверка на количество запросов (защита от DDoS)
     * Простая реализация rate limiting
     */
    public static class RateLimiter {
        private static final java.util.Map<String, RateLimitInfo> requestCounts =
            new java.util.concurrent.ConcurrentHashMap<>();

        private static class RateLimitInfo {
            int count;
            long timestamp;

            RateLimitInfo() {
                this.count = 1;
                this.timestamp = System.currentTimeMillis();
            }
        }

        /**
         * Проверяет не превышен ли лимит запросов
         * @param identifier уникальный идентификатор (IP, username)
         * @param maxRequests максимальное количество запросов
         * @param timeWindowMs временное окно в миллисекундах
         * @return true если лимит не превышен
         */
        public static boolean checkRateLimit(String identifier, int maxRequests, long timeWindowMs) {
            long now = System.currentTimeMillis();
            RateLimitInfo info = requestCounts.get(identifier);

            if (info == null) {
                requestCounts.put(identifier, new RateLimitInfo());
                return true;
            }

            // Проверяем истек ли временной интервал
            if (now - info.timestamp > timeWindowMs) {
                info.count = 1;
                info.timestamp = now;
                return true;
            }

            // Увеличиваем счетчик
            info.count++;

            // Проверяем лимит
            return info.count <= maxRequests;
        }

        /**
         * Очистка старых записей (вызывать периодически)
         */
        public static void cleanup() {
            long now = System.currentTimeMillis();
            requestCounts.entrySet().removeIf(entry ->
                now - entry.getValue().timestamp > 60000 // 1 минута
            );
        }
    }
}
