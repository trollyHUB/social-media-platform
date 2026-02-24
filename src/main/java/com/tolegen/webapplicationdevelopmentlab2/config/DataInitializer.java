package com.tolegen.webapplicationdevelopmentlab2.config;

import com.tolegen.webapplicationdevelopmentlab2.model.Post;
import com.tolegen.webapplicationdevelopmentlab2.model.User;
import com.tolegen.webapplicationdevelopmentlab2.repository.PostRepository;
import com.tolegen.webapplicationdevelopmentlab2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Инициализация тестовых данных при запуске.
 * Создаёт пользователей с паролями + посты.
 *
 * ТЕСТОВЫЕ АККАУНТЫ:
 *   admin@nexis.com     / admin123   (ADMIN)
 *   moder@nexis.com     / moder123   (MODERATOR)
 *   tolegen@nexis.com   / test123    (USER)
 *   aisha@nexis.com     / test123    (USER)
 *   arman@nexis.com     / test123    (USER)
 *   ... и другие
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            log.info("📦 Инициализация тестовых данных...");
            initUsers();
            initPosts();
            log.info("✅ Загружено: {} пользователей, {} постов",
                    userRepository.count(), postRepository.count());
        } else {
            log.info("📋 Данные уже существуют: {} users, {} posts",
                    userRepository.count(), postRepository.count());
        }
    }

    private void initUsers() {
        // ===== ADMIN =====
        createUser("admin", "admin@nexis.com", "admin123",
                "⚡ Администратор платформы NEXIS",
                "#ef4444", User.Role.ADMIN);

        // ===== MODERATOR =====
        createUser("moderator", "moder@nexis.com", "moder123",
                "🛡️ Модератор контента NEXIS",
                "#f97316", User.Role.MODERATOR);

        // ===== USERS =====
        createUser("tolegen", "tolegen@nexis.com", "test123",
                "Java Developer 🚀 | Spring Boot & Microservices", "#6c63ff", User.Role.USER);
        createUser("aisha", "aisha@nexis.com", "test123",
                "UI/UX Designer ✨ | Figma & Tailwind CSS", "#11998e", User.Role.USER);
        createUser("arman", "arman@nexis.com", "test123",
                "Student at IITU 📚 | Backend Developer", "#4facfe", User.Role.USER);
        createUser("dana", "dana@nexis.com", "test123",
                "Frontend Developer 💻 | React 19 & TypeScript", "#f093fb", User.Role.USER);
        createUser("nurlan", "nurlan@nexis.com", "test123",
                "DevOps Engineer 🔧 | Docker & Kubernetes", "#f7971e", User.Role.USER);
        createUser("saule", "saule@nexis.com", "test123",
                "Data Scientist 📊 | Python & Machine Learning", "#a18cd1", User.Role.USER);
        createUser("azamat", "azamat@nexis.com", "test123",
                "Mobile Developer 📱 | Android & Kotlin", "#ee0979", User.Role.USER);
        createUser("laura", "laura@nexis.com", "test123",
                "QA Engineer 🧪 | Selenium & TestNG", "#43e97b", User.Role.USER);
        createUser("dias", "dias@nexis.com", "test123",
                "Tech Lead 👨‍💼 | 10+ лет опыта в IT", "#6c63ff", User.Role.USER);
        createUser("aigerim", "aigerim@nexis.com", "test123",
                "Product Manager 🎯 | Agile & Scrum Master", "#f5576c", User.Role.USER);
    }

    private void initPosts() {
        createPost("admin",    "🚀 NEXIS Connect запущен! Добро пожаловать на платформу нового поколения. Здесь нет рекламы — только ты и люди, которым интересно то же самое.");
        createPost("tolegen",  "Привет всем! 👋 Сегодня закончил интеграцию JWT авторизации в Spring Boot 3. Если кто изучает Security — пишите, разберём вместе!");
        createPost("aisha",    "Только что выложила новый кейс на Behance — редизайн мобильного банкинга. Какой стиль вам ближе: минимализм или насыщенный UI? 🎨");
        createPost("arman",    "Сдал дипломный проект на Spring Boot + React! 3 месяца работы — и вот результат. Если кто ищет напарника для пет-проекта — я в деле 💪");
        createPost("dana",     "React 19 — это что-то! Попробовала новый use() хук, и код стал намного чище. Кто уже мигрировал с 18? Делитесь опытом 🔥");
        createPost("nurlan",   "Настроил k8s кластер с 0 до прода за 2 дня. Главный урок: не пытайтесь оптимизировать до того, как запустились. Ship first! 🚢");
        createPost("saule",    "Моя ML-модель наконец добилась 97% accuracy на тестовой выборке. XGBoost + грамотный feature engineering = магия 📈✨");
        createPost("azamat",   "Опубликовал первое Android-приложение в Play Store! 50 скачиваний за первый день — для меня это победа 🏆 Jetpack Compose рулит!");
        createPost("dias",     "Ищем Senior Java Developer в наш стартап. Стек: Spring Boot, Kafka, PostgreSQL. Удалёнка, справедливая зарплата. DM если интересно 💼");
        createPost("aigerim",  "Провела ретроспективу спринта — команда сама назвала 3 главные проблемы и предложила решения. Вот это называется самоорганизация! 🎯");
    }

    private void createUser(String username, String email, String password,
                             String bio, String avatarColor, User.Role role) {
        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .bio(bio)
                .avatarColor(avatarColor)
                .role(role)
                .enabled(true)
                .build();
        userRepository.save(user);
        log.info("  ✅ User: {} [{}]", username, role);
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
