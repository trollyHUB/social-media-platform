package com.tolegen.webapplicationdevelopmentlab2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Главный класс Spring Boot приложения
 * Встроенный Tomcat сервер запускается автоматически
 *
 * @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan
 */
@SpringBootApplication
@EntityScan("com.tolegen.webapplicationdevelopmentlab2.model")
@EnableJpaRepositories("com.tolegen.webapplicationdevelopmentlab2.repository")
public class SocialMediaPlatformApplication {

    public static void main(String[] args) {
        System.out.println("🚀 Запуск Social Media Platform...");
        System.out.println("📦 Spring Boot со встроенным Tomcat");
        System.out.println("🐘 PostgreSQL на порту 5434");
        System.out.println("🌐 Сервер будет доступен на: http://localhost:8090");

        SpringApplication.run(SocialMediaPlatformApplication.class, args);

        System.out.println("✅ Приложение успешно запущено!");
        System.out.println("📖 Откройте: http://localhost:8090/");
    }

    /**
     * Настройка CORS для REST API и статических ресурсов
     */
    @Bean
    public WebMvcConfigurer webConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .maxAge(3600);
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/css/**")
                        .addResourceLocations("classpath:/static/css/");
                registry.addResourceHandler("/js/**")
                        .addResourceLocations("classpath:/static/js/");
            }
        };
    }
}
