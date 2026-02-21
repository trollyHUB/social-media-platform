package com.tolegen.webapplicationdevelopmentlab2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * JPA Entity для поста в социальной сети
 * @Data от Lombok генерирует геттеры, сеттеры, toString, equals, hashCode
 */
@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String author;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private Integer likes = 0;

    @Column(name = "comments_count", nullable = false)
    private Integer commentsCount = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.likes == null) this.likes = 0;
        if (this.commentsCount == null) this.commentsCount = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Дополнительные методы для совместимости
    public void addLike() {
        this.likes++;
    }

    public void incrementComments() {
        this.commentsCount++;
    }

    public String getFormattedDate() {
        if (createdAt == null) return "";
        return createdAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public String getRelativeTime() {
        if (createdAt == null) return "";

        long minutes = java.time.Duration.between(createdAt, LocalDateTime.now()).toMinutes();

        if (minutes < 1) return "только что";
        if (minutes < 60) return minutes + " мин. назад";

        long hours = minutes / 60;
        if (hours < 24) return hours + " ч. назад";

        long days = hours / 24;
        if (days == 1) return "вчера";
        if (days < 7) return days + " дн. назад";

        return getFormattedDate();
    }
}

