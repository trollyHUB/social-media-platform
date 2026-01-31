package com.tolegen.webapplicationdevelopmentlab2.model;

import java.time.LocalDateTime;

/**
 * Модель комментария к посту
 */
public class Comment {
    private int id;
    private int postId;
    private String author;
    private String content;
    private LocalDateTime createdAt;

    public Comment() {
        this.createdAt = LocalDateTime.now();
    }

    public Comment(int id, int postId, String author, String content) {
        this.id = id;
        this.postId = postId;
        this.author = author;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getRelativeTime() {
        LocalDateTime now = LocalDateTime.now();
        long minutes = java.time.Duration.between(createdAt, now).toMinutes();

        if (minutes < 1) return "только что";
        if (minutes < 60) return minutes + " мин. назад";

        long hours = minutes / 60;
        if (hours < 24) return hours + " ч. назад";

        long days = hours / 24;
        if (days < 7) return days + " дн. назад";

        return days / 7 + " нед. назад";
    }
}
