package com.tolegen.webapplicationdevelopmentlab2.controller;

import com.tolegen.webapplicationdevelopmentlab2.dto.AuthResponse;
import com.tolegen.webapplicationdevelopmentlab2.model.User;
import com.tolegen.webapplicationdevelopmentlab2.repository.UserRepository;
import com.tolegen.webapplicationdevelopmentlab2.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Admin API — только для пользователей с ролью ADMIN
 * Доступно по: /api/admin/**
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AdminController {

    private final UserRepository userRepository;
    private final PostService postService;

    /** GET /api/admin/users — все пользователи */
    @GetMapping("/users")
    public ResponseEntity<List<AuthResponse.UserDto>> getAllUsers() {
        List<AuthResponse.UserDto> users = userRepository.findAll()
                .stream()
                .map(AuthResponse.UserDto::from)
                .toList();
        return ResponseEntity.ok(users);
    }

    /** GET /api/admin/stats — расширенная статистика */
    @GetMapping("/stats")
    public ResponseEntity<?> getAdminStats() {
        long totalUsers = userRepository.count();
        long adminCount = userRepository.countByRole(User.Role.ADMIN);
        long modCount = userRepository.countByRole(User.Role.MODERATOR);
        long disabledCount = userRepository.countByEnabled(false);
        long totalPosts = postService.getTotalPostsCount();

        return ResponseEntity.ok(Map.of(
                "totalUsers", totalUsers,
                "admins", adminCount,
                "moderators", modCount,
                "disabledUsers", disabledCount,
                "totalPosts", totalPosts
        ));
    }

    /** PUT /api/admin/users/{id}/role — изменить роль пользователя */
    @PutMapping("/users/{id}/role")
    public ResponseEntity<?> changeRole(@PathVariable Long id,
                                        @RequestBody Map<String, String> body) {
        String roleName = body.get("role");
        try {
            User.Role role = User.Role.valueOf(roleName.toUpperCase());
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
            user.setRole(role);
            userRepository.save(user);
            log.info("Admin: роль пользователя {} изменена на {}", user.getUsername(), role);
            return ResponseEntity.ok(Map.of("message", "Роль изменена", "role", role.name()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Неверная роль: " + roleName));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /** PUT /api/admin/users/{id}/toggle-ban — заблокировать/разблокировать */
    @PutMapping("/users/{id}/toggle-ban")
    public ResponseEntity<?> toggleBan(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        user.setEnabled(!user.getEnabled());
        userRepository.save(user);
        String status = user.getEnabled() ? "разблокирован" : "заблокирован";
        log.info("Admin: пользователь {} {}", user.getUsername(), status);
        return ResponseEntity.ok(Map.of(
                "message", "Пользователь " + status,
                "enabled", user.getEnabled()
        ));
    }

    /** DELETE /api/admin/users/{id} — удалить пользователя */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        log.info("Admin: пользователь ID={} удалён", id);
        return ResponseEntity.ok(Map.of("message", "Пользователь удалён"));
    }

    /** DELETE /api/admin/posts/{id} — удалить любой пост */
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        boolean deleted = postService.deletePost(id);
        if (deleted) {
            log.info("Admin: пост ID={} удалён", id);
            return ResponseEntity.ok(Map.of("message", "Пост удалён"));
        }
        return ResponseEntity.notFound().build();
    }
}
