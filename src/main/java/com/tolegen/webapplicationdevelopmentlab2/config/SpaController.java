package com.tolegen.webapplicationdevelopmentlab2.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SPA Fallback Controller
 *
 * Перенаправляет все НЕ-API запросы на index.html
 * Это нужно чтобы React Router работал при прямом переходе по URL.
 *
 * Пример: пользователь открывает http://localhost:8090/profile/1
 * → Spring Boot не знает такой маршрут
 * → этот контроллер отдаёт index.html
 * → React Router сам обрабатывает /profile/1
 */
@Controller
public class SpaController {

    /**
     * Ловит все запросы которые:
     * - НЕ начинаются с /api/
     * - НЕ являются статическими файлами (js, css, png...)
     * - НЕ являются /api/health и т.д.
     */
    @RequestMapping(value = {
        "/", "/feed", "/login", "/register",
        "/profile/**", "/post/**", "/search",
        "/notifications", "/messages/**",
        "/settings", "/admin"
    })
    public String forwardToIndex() {
        return "forward:/index.html";
    }
}
