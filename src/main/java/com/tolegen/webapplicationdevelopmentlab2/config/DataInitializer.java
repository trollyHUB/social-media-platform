package com.tolegen.webapplicationdevelopmentlab2.config;

import com.tolegen.webapplicationdevelopmentlab2.model.Post;
import com.tolegen.webapplicationdevelopmentlab2.model.User;
import com.tolegen.webapplicationdevelopmentlab2.repository.PostRepository;
import com.tolegen.webapplicationdevelopmentlab2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Инициализация тестовых данных при запуске приложения
 * Работает ТОЛЬКО если таблицы пустые
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            log.info("📦 Инициализация тестовых данных...");
            initUsers();
            initPosts();
            log.info("✅ Тестовые данные загружены: {} пользователей, {} постов",
                    userRepository.count(), postRepository.count());
        } else {
            log.info("📋 Данные уже существуют: {} пользователей, {} постов",
                    userRepository.count(), postRepository.count());
        }
    }

    private void initUsers() {
        createUser("tolegen", "tolegen@example.com", "Java Developer 🚀 | Spring Boot");
        createUser("aisha", "aisha@example.com", "Web Designer ✨ | UI/UX специалист");
        createUser("arman", "arman@example.com", "Student at IITU 📚 | Backend разработка");
        createUser("dana", "dana@example.com", "Frontend Developer 💻 | React & Vue.js");
        createUser("nurlan", "nurlan@example.com", "DevOps Engineer 🔧 | Docker & K8s");
        createUser("saule", "saule@example.com", "Data Scientist 📊 | Python & ML");
        createUser("azamat", "azamat@example.com", "Mobile Developer 📱 | Android & iOS");
        createUser("laura", "laura@example.com", "QA Engineer 🧪 | Automation Testing");
        createUser("dias", "dias@example.com", "Tech Lead 👨‍💼 | 10+ лет опыта");
        createUser("aigerim", "aigerim@example.com", "Product Manager 🎯 | Agile & Scrum");
        createUser("bekzat", "bekzat@example.com", "Security Engineer 🔐 | Ethical Hacking");
        createUser("gulnara", "gulnara@example.com", "Full Stack Developer | Node.js & React");
    }

    private void initPosts() {
        createPost("tolegen", "Привет всем! 👋 Начинаю изучать Spring Boot REST API!");
        createPost("aisha", "Сегодня закончила новый дизайн! 🎨 Figma + Tailwind CSS ✨");
        createPost("arman", "Кто-нибудь может порекомендовать ресурсы по Spring Data JPA? 📚");
        createPost("dana", "Закончила курс по React Hooks и TypeScript! 🚀");
        createPost("nurlan", "CI/CD pipeline на GitHub Actions настроен! Deployment за 2 минуты! ⚡");
        createPost("saule", "ML модель с accuracy 95%! Использовала XGBoost + Feature Engineering 📈");
        createPost("azamat", "Новое Android приложение в Google Play! Jetpack Compose 📱");
        createPost("laura", "Автоматизировала 80% тестов с Selenium + TestNG! 🧪");
        createPost("dias", "Ищу Senior Java Developer для проекта на Spring Boot! 💼");
        createPost("aigerim", "Отличный sprint planning сегодня! Команда в полном составе 🎯");
        createPost("bekzat", "Нашёл SQL injection уязвимость, ответственно раскрыл автору! 🔐");
        createPost("gulnara", "Full stack проект на React + Node.js + PostgreSQL завершён! 🎉");
    }

    private void createUser(String username, String email, String bio) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setBio(bio);
        userRepository.save(user);
    }

    private void createPost(String author, String content) {
        Post post = new Post();
        post.setAuthor(author);
        post.setContent(content);
        post.setLikes(0);
        post.setCommentsCount(0);
        postRepository.save(post);
    }
}
