package com.tolegen.webapplicationdevelopmentlab2.controller;

import com.tolegen.webapplicationdevelopmentlab2.dto.CreateCommentRequest;
import com.tolegen.webapplicationdevelopmentlab2.dto.CreatePostRequest;
import com.tolegen.webapplicationdevelopmentlab2.dto.CreateUserRequest;
import com.tolegen.webapplicationdevelopmentlab2.model.Comment;
import com.tolegen.webapplicationdevelopmentlab2.model.Post;
import com.tolegen.webapplicationdevelopmentlab2.model.User;
import com.tolegen.webapplicationdevelopmentlab2.service.CommentService;
import com.tolegen.webapplicationdevelopmentlab2.service.PostService;
import com.tolegen.webapplicationdevelopmentlab2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * REST API Controller для Social Media Platform
 *
 * Полный список эндпоинтов:
 *
 * POSTS:
 *   GET    /api/posts           - Получить все посты (опционально ?author=xxx)
 *   GET    /api/posts/{id}      - Получить пост по ID
 *   POST   /api/posts           - Создать новый пост (JSON body)
 *   PUT    /api/posts/{id}      - Обновить пост
 *   DELETE /api/posts/{id}      - Удалить пост
 *   POST   /api/posts/{id}/like - Лайкнуть пост
 *
 * USERS:
 *   GET    /api/users           - Получить всех пользователей
 *   GET    /api/users/{id}      - Получить пользователя по ID
 *   POST   /api/users           - Создать нового пользователя (JSON body)
 *   DELETE /api/users/{id}      - Удалить пользователя
 *
 * COMMENTS:
 *   GET    /api/posts/{postId}/comments - Комментарии поста
 *   POST   /api/posts/{postId}/comments - Добавить комментарий
 *   DELETE /api/comments/{id}           - Удалить комментарий
 *
 * OTHER:
 *   GET    /api/stats           - Статистика платформы
 *   GET    /api/health          - Проверка работоспособности
 *
 * @RestController = @Controller + @ResponseBody (автоматическая JSON сериализация через Jackson)
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class SocialMediaApiController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    // ==================== POSTS API ====================

    /**
     * GET /api/posts - получить все посты
     */
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts(
            @RequestParam(required = false) String author) {

        log.info("📡 GET /api/posts (author={})", author);

        List<Post> posts = (author != null && !author.isEmpty())
                ? postService.getPostsByAuthor(author)
                : postService.getAllPosts();

        return ResponseEntity.ok(posts);
    }

    /**
     * GET /api/posts/{id} - получить пост по ID
     */
    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        log.info("📡 GET /api/posts/{}", id);

        return postService.getPostById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Пост не найден", "id", id)));
    }

    /**
     * POST /api/posts - создать новый пост
     */
    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostRequest request) {
        log.info("📡 POST /api/posts (author={}, content={})",
                request.getAuthor(), request.getContent());

        try {
            Post post = postService.createPost(request.getAuthor(), request.getContent());
            return ResponseEntity.status(HttpStatus.CREATED).body(post);
        } catch (Exception e) {
            log.error("❌ Ошибка создания поста: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * PUT /api/posts/{id} - обновить пост
     */
    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody CreatePostRequest request) {

        log.info("📡 PUT /api/posts/{}", id);

        try {
            Post updated = postService.updatePost(id, request.getContent());
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Пост не найден"));
        }
    }

    /**
     * DELETE /api/posts/{id} - удалить пост
     */
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        log.info("📡 DELETE /api/posts/{}", id);

        boolean deleted = postService.deletePost(id);

        if (deleted) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Пост удалён",
                    "id", id
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Пост не найден"));
        }
    }

    /**
     * POST /api/posts/{id}/like - лайкнуть пост
     */
    @PostMapping("/posts/{id}/like")
    public ResponseEntity<?> likePost(@PathVariable Long id) {
        log.info("📡 POST /api/posts/{}/like", id);

        try {
            Post post = postService.likePost(id);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Пост не найден"));
        }
    }

    // ==================== COMMENTS API ====================

    /**
     * GET /api/posts/{postId}/comments - получить комментарии поста
     */
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<?> getComments(@PathVariable Long postId) {
        log.info("📡 GET /api/posts/{}/comments", postId);

        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    /**
     * POST /api/posts/{postId}/comments - добавить комментарий
     */
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<?> addComment(
            @PathVariable Long postId,
            @Valid @RequestBody CreateCommentRequest request) {

        log.info("📡 POST /api/posts/{}/comments (author={})", postId, request.getAuthor());

        try {
            Comment comment = commentService.addComment(postId, request.getAuthor(), request.getContent());
            return ResponseEntity.status(HttpStatus.CREATED).body(comment);
        } catch (Exception e) {
            log.error("❌ Ошибка добавления комментария: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * DELETE /api/comments/{id} - удалить комментарий
     */
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        log.info("📡 DELETE /api/comments/{}", id);

        boolean deleted = commentService.deleteComment(id);

        if (deleted) {
            return ResponseEntity.ok(Map.of("success", true, "message", "Комментарий удалён"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Комментарий не найден"));
        }
    }

    // ==================== USERS API ====================

    /**
     * GET /api/users - получить всех пользователей
     */
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(required = false) String username) {

        log.info("📡 GET /api/users (username={})", username);

        if (username != null && !username.isEmpty()) {
            return userService.findByUsername(username)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("error", "Пользователь не найден", "username", username)));
        }

        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * GET /api/users/{id} - получить пользователя по ID
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        log.info("📡 GET /api/users/{}", id);

        return userService.getUserById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Пользователь не найден", "id", id)));
    }

    /**
     * POST /api/users - создать нового пользователя
     * Нельзя создать пользователя с существующим username или email!
     */
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("📡 POST /api/users (username={}, email={})",
                request.getUsername(), request.getEmail());

        try {
            User user = userService.createUser(
                    request.getUsername(),
                    request.getEmail(),
                    request.getBio()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            log.error("❌ Ошибка создания пользователя: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * DELETE /api/users/{id} - удалить пользователя
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        log.info("📡 DELETE /api/users/{}", id);

        boolean deleted = userService.deleteUser(id);

        if (deleted) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Пользователь удалён",
                    "id", id
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Пользователь не найден"));
        }
    }

    // ==================== STATISTICS API ====================

    /**
     * GET /api/stats - статистика платформы
     */
    @GetMapping("/stats")
    public ResponseEntity<?> getStatistics() {
        log.info("📡 GET /api/stats");

        return ResponseEntity.ok(Map.of(
                "totalUsers", userService.getTotalUsersCount(),
                "totalPosts", postService.getTotalPostsCount(),
                "totalLikes", postService.getTotalLikes(),
                "totalComments", commentService.getTotalCommentsCount(),
                "timestamp", LocalDateTime.now().toString()
        ));
    }

    /**
     * GET /api/health - проверка работоспособности
     */
    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "message", "Social Media Platform работает!",
                "server", "Spring Boot + встроенный Tomcat",
                "database", "PostgreSQL",
                "port", 8090,
                "timestamp", LocalDateTime.now().toString()
        ));
    }
}
