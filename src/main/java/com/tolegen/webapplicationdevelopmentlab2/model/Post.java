package com.tolegen.webapplicationdevelopmentlab2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 * Модель поста в социальной сети
 * Post model for Social Media Platform
 */
public class Post {
    private int id;
    private String author;
    private String content;
    private int likes;
    private LocalDateTime createdAt;
    private Set<String> likedBy; // Кто лайкнул пост
    private int commentsCount; // Количество комментариев

    public Post() {
        this.createdAt = LocalDateTime.now();
        this.likes = 0;
        this.likedBy = new HashSet<>();
        this.commentsCount = 0;
    }

    public Post(int id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.likes = 0;
        this.createdAt = LocalDateTime.now();
        this.likedBy = new HashSet<>();
        this.commentsCount = 0;
    }

    // ...existing code...

    public void addLike() {
        this.likes++;
    }

    public boolean addLikeBy(String username) {
        if (likedBy.add(username)) {
            this.likes++;
            return true;
        }
        return false;
    }

    public boolean hasLikedBy(String username) {
        return likedBy.contains(username);
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public void incrementComments() {
        this.commentsCount++;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return createdAt.format(formatter);
    }

    public String getDetailedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm:ss");
        return createdAt.format(formatter);
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

        long weeks = days / 7;
        if (weeks < 4) return weeks + " нед. назад";

        return getFormattedDate();
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                ", createdAt=" + createdAt +
                '}';
    }
}
