package com.tolegen.webapplicationdevelopmentlab2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * JPA Entity для пользователя социальной сети
 * @Data от Lombok генерирует геттеры, сеттеры, toString, equals, hashCode
 */
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 500)
    private String bio;

    @Column(name = "avatar_color", length = 100)
    private String avatarColor = "#667eea, #764ba2";

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "last_active")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastActive;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.lastActive = LocalDateTime.now();
        if (this.avatarColor == null) {
            this.avatarColor = generateRandomColor();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastActive = LocalDateTime.now();
    }

    private String generateRandomColor() {
        String[] colors = {
                "#667eea, #764ba2",
                "#11998e, #38ef7d",
                "#ee0979, #ff6a00",
                "#f093fb, #f5576c",
                "#4facfe, #00f2fe"
        };
        return colors[(int) (Math.random() * colors.length)];
    }

    public boolean isOnline() {
        if (lastActive == null) return false;
        long minutes = java.time.Duration.between(lastActive, LocalDateTime.now()).toMinutes();
        return minutes < 5;
    }

    // Для совместимости со старым кодом
    public LocalDateTime getRegisteredAt() {
        return createdAt;
    }
}

