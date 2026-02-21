-- =====================================================
-- SQL Script для инициализации базы данных
-- Social Media Platform - socialmediaplatform
-- PostgreSQL 16
-- Выполните этот скрипт в psql или pgAdmin
-- =====================================================

-- Удаление существующих таблиц (если нужно пересоздать)
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS posts CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- =====================================================
-- Таблица пользователей
-- =====================================================
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    bio TEXT DEFAULT 'Новый пользователь',
    avatar_color VARCHAR(100) DEFAULT '#667eea, #764ba2',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_active TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT username_length CHECK (LENGTH(username) >= 3),
    CONSTRAINT email_format CHECK (email LIKE '%@%')
);

-- Индексы для users
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_created ON users(created_at DESC);

-- =====================================================
-- Таблица постов
-- =====================================================
CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    author VARCHAR(50) NOT NULL,
    content TEXT NOT NULL,
    likes INTEGER DEFAULT 0,
    comments_count INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (author) REFERENCES users(username) ON DELETE CASCADE,
    CONSTRAINT content_not_empty CHECK (LENGTH(TRIM(content)) > 0),
    CONSTRAINT likes_positive CHECK (likes >= 0)
);

-- Индексы для posts
CREATE INDEX idx_posts_author ON posts(author);
CREATE INDEX idx_posts_created ON posts(created_at DESC);
CREATE INDEX idx_posts_likes ON posts(likes DESC);

-- =====================================================
-- Таблица комментариев
-- =====================================================
CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    post_id INTEGER NOT NULL,
    author VARCHAR(50) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    CONSTRAINT comment_content_not_empty CHECK (LENGTH(TRIM(content)) > 0)
);

-- Индексы для comments
CREATE INDEX idx_comments_post ON comments(post_id);
CREATE INDEX idx_comments_author ON comments(author);
CREATE INDEX idx_comments_created ON comments(created_at DESC);

-- =====================================================
-- Вставка пользователей (10+ человек)
-- =====================================================
INSERT INTO users (username, email, bio, avatar_color) VALUES
('tolegen', 'tolegen@example.com', 'Java Developer 🚀 | Люблю Spring Boot и микросервисы', '#667eea, #764ba2'),
('aisha', 'aisha@example.com', 'Web Designer ✨ | UI/UX специалист', '#11998e, #38ef7d'),
('arman', 'arman@example.com', 'Student at IITU 📚 | Изучаю Backend разработку', '#ee0979, #ff6a00'),
('dana', 'dana@example.com', 'Frontend Developer 💻 | React & Vue.js эксперт', '#f093fb, #f5576c'),
('nurlan', 'nurlan@example.com', 'DevOps Engineer 🔧 | Kubernetes & Docker', '#4facfe, #00f2fe'),
('saule', 'saule@example.com', 'Data Scientist 📊 | Python & Machine Learning', '#667eea, #764ba2'),
('azamat', 'azamat@example.com', 'Mobile Developer 📱 | Android & iOS', '#11998e, #38ef7d'),
('laura', 'laura@example.com', 'QA Engineer 🧪 | Automation Testing', '#ee0979, #ff6a00'),
('dias', 'dias@example.com', 'Tech Lead 👨‍💼 | 10+ лет опыта', '#f093fb, #f5576c'),
('aigerim', 'aigerim@example.com', 'Product Manager 🎯 | Agile & Scrum', '#4facfe, #00f2fe'),
('bekzat', 'bekzat@example.com', 'Security Engineer 🔐 | Ethical Hacking', '#667eea, #764ba2'),
('gulnara', 'gulnara@example.com', 'Full Stack Developer | Node.js & React', '#11998e, #38ef7d');

-- =====================================================
-- Вставка постов
-- =====================================================
INSERT INTO posts (author, content, likes, created_at) VALUES
('tolegen', 'Привет всем! 👋 Начинаю изучать Jakarta Servlets. Очень интересная технология для веб-разработки!', 12, NOW() - INTERVAL '7 days'),
('aisha', 'Сегодня закончила новый дизайн для мобильного приложения! 🎨 Очень довольна результатом ✨', 25, NOW() - INTERVAL '6 days'),
('arman', 'Кто-нибудь может порекомендовать хорошие ресурсы по изучению Spring Framework? 📚', 8, NOW() - INTERVAL '5 days'),
('dana', 'Только что закончила курс по React Hooks! Теперь мой код намного чище 🚀', 18, NOW() - INTERVAL '4 days'),
('nurlan', 'Настроил CI/CD pipeline с помощью Jenkins и Docker. Deployment теперь занимает 2 минуты! ⚡', 30, NOW() - INTERVAL '3 days'),
('saule', 'Работаю над моделью машинного обучения для предсказания цен. Accuracy уже 92%! 📈', 22, NOW() - INTERVAL '2 days'),
('azamat', 'Релиз нового приложения на Android! Скачивайте и оставляйте отзывы 📱', 15, NOW() - INTERVAL '1 day'),
('laura', 'Автоматизировала 80% тестов. Теперь можем запускать регрессию за 15 минут! 🧪', 19, NOW() - INTERVAL '20 hours'),
('dias', 'Ищу Senior Java Developer в нашу команду. Интересные проекты и хорошие условия! 💼', 11, NOW() - INTERVAL '16 hours'),
('aigerim', 'Провели отличный sprint planning! Команда замотивирована и готова к новым челленджам 🎯', 27, NOW() - INTERVAL '12 hours'),
('tolegen', 'Изучаю паттерны проектирования. Singleton, Factory, Observer - все такое увлекательное! 🤓', 14, NOW() - INTERVAL '8 hours'),
('aisha', 'Новый тренд в UI - glassmorphism! Попробовала применить в своем проекте, выглядит потрясающе! ✨', 33, NOW() - INTERVAL '6 hours'),
('dana', 'Переписала проект с JavaScript на TypeScript. Количество багов сократилось в 3 раза! 💪', 28, NOW() - INTERVAL '4 hours'),
('nurlan', 'Kubernetes - это магия! Автоскейлинг работает как часы ⚙️', 21, NOW() - INTERVAL '3 hours'),
('arman', 'Сдал экзамен по базам данных на отлично! Следующий - веб-технологии 📖', 16, NOW() - INTERVAL '2 hours'),
('bekzat', 'Обнаружил интересную уязвимость SQL injection в opensource проекте. Репортировал автору! 🔐', 20, NOW() - INTERVAL '1 hour'),
('gulnara', 'Full stack проект готов! Frontend на React, Backend на Node.js, БД PostgreSQL 🎉', 35, NOW() - INTERVAL '30 minutes'),
('saule', 'Python DataFrame magic: одна строка кода заменила 50 строк SQL! 🐍', 24, NOW() - INTERVAL '15 minutes'),
('dias', 'Code review - это не проверка кода, это обмен знаниями! 💡', 29, NOW() - INTERVAL '5 minutes'),
('aigerim', 'Новая фича в production! Пользователи довольны, метрики растут 📊', 17, NOW());

-- =====================================================
-- Обновляем comments_count для постов
-- =====================================================
INSERT INTO comments (post_id, author, content, created_at) VALUES
(1, 'aisha', 'Отличный старт! Jakarta Servlets - мощная технология!', NOW() - INTERVAL '6 days'),
(1, 'arman', 'Я тоже изучаю! Давай вместе!', NOW() - INTERVAL '6 days'),
(2, 'dana', 'Покажи дизайн! Очень интересно посмотреть 👀', NOW() - INTERVAL '5 days'),
(2, 'tolegen', 'Выглядит профессионально! 💯', NOW() - INTERVAL '5 days'),
(3, 'dias', 'Официальная документация Spring - лучший ресурс. Плюс Baeldung.com', NOW() - INTERVAL '4 days'),
(3, 'gulnara', 'YouTube канал "Java Brains" - очень хорош!', NOW() - INTERVAL '4 days'),
(4, 'bekzat', 'TypeScript - это обязательно для серьёзных проектов!', NOW() - INTERVAL '3 days'),
(5, 'azamat', 'Docker + Kubernetes = DevOps мечта!', NOW() - INTERVAL '2 days'),
(6, 'laura', '92% accuracy - это отлично! Какой алгоритм используешь?', NOW() - INTERVAL '1 day'),
(6, 'tolegen', 'Machine Learning - это будущее!', NOW() - INTERVAL '1 day'),
(7, 'aigerim', 'Скачала! Очень удобное приложение!', NOW() - INTERVAL '20 hours'),
(8, 'dias', 'Автоматизация тестов - это ключ к качеству!', NOW() - INTERVAL '16 hours'),
(9, 'nurlan', 'Отправил резюме! Очень интересно!', NOW() - INTERVAL '12 hours'),
(10, 'saule', 'Agile forever!', NOW() - INTERVAL '10 hours'),
(11, 'aisha', 'Паттерны - основа хорошего кода!', NOW() - INTERVAL '6 hours'),
(12, 'dana', 'Glassmorphism очень красивый эффект!', NOW() - INTERVAL '5 hours'),
(16, 'tolegen', 'Важная работа! Security прежде всего!', NOW() - INTERVAL '45 minutes'),
(17, 'arman', 'Вдохновляет! Хочу также!', NOW() - INTERVAL '20 minutes'),
(19, 'aigerim', 'Согласна! Code review улучшает команду!', NOW() - INTERVAL '3 minutes'),
(20, 'bekzat', 'Поздравляю с релизом!', NOW());

-- Обновляем счётчики comments_count
UPDATE posts SET comments_count = (
    SELECT COUNT(*) FROM comments WHERE comments.post_id = posts.id
);

-- =====================================================
-- Просмотр данных
-- =====================================================
SELECT 'users' as table_name, COUNT(*) as count FROM users
UNION ALL
SELECT 'posts' as table_name, COUNT(*) as count FROM posts
UNION ALL
SELECT 'comments' as table_name, COUNT(*) as count FROM comments;

SELECT '✅ База данных socialmediaplatform успешно инициализирована!' as status;
