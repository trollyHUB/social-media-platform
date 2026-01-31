package com.tolegen.webapplicationdevelopmentlab2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс для управления социальной сетью
 * Social Media Platform management class
 */
public class SocialMedia {
    private final List<User> users;
    private final List<Post> posts;
    private final List<Comment> comments;
    private int nextUserId;
    private int nextPostId;
    private int nextCommentId;

    private static SocialMedia instance;

    private SocialMedia() {
        this.users = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.nextUserId = 1;
        this.nextPostId = 1;
        this.nextCommentId = 1;
        initSampleData();
    }

    public static synchronized SocialMedia getInstance() {
        if (instance == null) {
            instance = new SocialMedia();
        }
        return instance;
    }

    private void initSampleData() {
        // Добавляем тестовых пользователей
        addUser("tolegen", "tolegen@example.com", "Java Developer 🚀 | Люблю Spring Boot и микросервисы");
        addUser("aisha", "aisha@example.com", "Web Designer ✨ | UI/UX специалист");
        addUser("arman", "arman@example.com", "Student at IITU 📚 | Изучаю Backend разработку");
        addUser("dana", "dana@example.com", "Frontend Developer 💻 | React & Vue.js эксперт");
        addUser("nurlan", "nurlan@example.com", "DevOps Engineer 🔧 | Kubernetes & Docker");
        addUser("saule", "saule@example.com", "Data Scientist 📊 | Python & Machine Learning");
        addUser("azamat", "azamat@example.com", "Mobile Developer 📱 | Android & iOS");
        addUser("laura", "laura@example.com", "QA Engineer 🧪 | Automation Testing");
        addUser("dias", "dias@example.com", "Tech Lead 👨‍💼 | 10+ лет опыта");
        addUser("aigerim", "aigerim@example.com", "Product Manager 🎯 | Agile & Scrum");

        // Добавляем разнообразные посты
        addPost("tolegen", "Привет всем! 👋 Начинаю изучать Jakarta Servlets. Очень интересная технология для веб-разработки!");
        addPost("aisha", "Сегодня закончила новый дизайн для мобильного приложения! 🎨 Очень довольна результатом ✨");
        addPost("arman", "Кто-нибудь может порекомендовать хорошие ресурсы по изучению Spring Framework? 📚");
        addPost("dana", "Только что закончила курс по React Hooks! Теперь мой код намного чище 🚀");
        addPost("nurlan", "Настроил CI/CD pipeline с помощью Jenkins и Docker. Deployment теперь занимает 2 минуты! ⚡");
        addPost("saule", "Работаю над моделью машинного обучения для предсказания цен. Accuracy уже 92%! 📈");
        addPost("azamat", "Релиз нового приложения на Android! Скачивайте и оставляйте отзывы 📱");
        addPost("laura", "Автоматизировала 80% тестов. Теперь можем запускать регрессию за 15 минут! 🧪");
        addPost("dias", "Ищу Senior Java Developer в нашу команду. Интересные проекты и хорошие условия! 💼");
        addPost("aigerim", "Провели отличный sprint planning! Команда замотивирована и готова к новым челленджам 🎯");
        addPost("tolegen", "Изучаю паттерны проектирования. Singleton, Factory, Observer - все такое увлекательное! 🤓");
        addPost("aisha", "Новый тренд в UI - glassmorphism! Попробовала применить в своем проекте, выглядит потрясающе! ✨");
        addPost("dana", "Переписала проект с JavaScript на TypeScript. Количество багов сократилось в 3 раза! 💪");
        addPost("nurlan", "Kubernetes - это магия! Автоскейлинг работает как часы ⚙️");
        addPost("arman", "Сдал экзамен по базам данных на отлично! Следующий - веб-технологии 📖");
    }

    // User methods
    public User addUser(String username, String email, String bio) {
        if (findUserByUsername(username).isPresent()) {
            return null;
        }
        if (users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email))) {
            return null;
        }
        User user = new User(nextUserId++, username, email, bio);
        users.add(user);
        return user;
    }

    // ...existing code...

    public boolean deletePost(int postId) {
        comments.removeIf(c -> c.getPostId() == postId);
        return posts.removeIf(p -> p.getId() == postId);
    }

    // Comment methods
    public Comment addComment(int postId, String author, String content) {
        Comment comment = new Comment(nextCommentId++, postId, author, content);
        comments.add(comment);
        findPostById(postId).ifPresent(Post::incrementComments);
        return comment;
    }

    public List<Comment> getCommentsByPostId(int postId) {
        return comments.stream()
                .filter(c -> c.getPostId() == postId)
                .collect(Collectors.toList());
    }

    public int getCommentsCountByPostId(int postId) {
        return (int) comments.stream()
                .filter(c -> c.getPostId() == postId)
                .count();
    }

    // Statistics
    public List<User> getTopUsers(int limit) {
        return users.stream()
                .sorted((u1, u2) -> Integer.compare(
                    getPostsByAuthor(u2.getUsername()).size(),
                    getPostsByAuthor(u1.getUsername()).size()
                ))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Post> getTrendingPosts(int limit) {
        return posts.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getLikes(), p1.getLikes()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public int getTotalLikes() {
        return posts.stream().mapToInt(Post::getLikes).sum();
    }

    public int getTotalComments() {
        return comments.size();
    }

    public Optional<User> findUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    // Post methods
    public Post addPost(String author, String content) {
        Post post = new Post(nextPostId++, author, content);
        posts.add(0, post); // Новые посты в начале списка
        return post;
    }

    public List<Post> getAllPosts() {
        return new ArrayList<>(posts);
    }

    public List<Post> getPostsByAuthor(String author) {
        return posts.stream()
                .filter(p -> p.getAuthor().equalsIgnoreCase(author))
                .toList();
    }

    public Optional<Post> findPostById(int id) {
        return posts.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    public void likePost(int postId) {
        findPostById(postId).ifPresent(Post::addLike);
    }


    public int getTotalPostsCount() {
        return posts.size();
    }

    public int getTotalUsersCount() {
        return users.size();
    }
}
