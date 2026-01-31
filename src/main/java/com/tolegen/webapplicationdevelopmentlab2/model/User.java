package com.tolegen.webapplicationdevelopmentlab2.model;

import java.time.LocalDateTime;

/**
 * Модель пользователя социальной сети
 * User model for Social Media Platform
 */
public class User {
    private int id;
    private String username;
    private String email;
    private String bio;
    private LocalDateTime registeredAt;
    private LocalDateTime lastActive;
    private String avatarColor; // Цвет аватара

    public User() {
        this.registeredAt = LocalDateTime.now();
        this.lastActive = LocalDateTime.now();
        this.avatarColor = generateRandomColor();
    }

    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.registeredAt = LocalDateTime.now();
        this.lastActive = LocalDateTime.now();
        this.avatarColor = generateRandomColor();
    }

    public User(int id, String username, String email, String bio) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.registeredAt = LocalDateTime.now();
        this.lastActive = LocalDateTime.now();
        this.avatarColor = generateRandomColor();
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
        return minutes < 5; // Онлайн если активность менее 5 минут назад
    }

    public void updateActivity() {
        this.lastActive = LocalDateTime.now();
    }

    // ...existing code...

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    public String getAvatarColor() {
        return avatarColor;
    }

    public void setAvatarColor(String avatarColor) {
        this.avatarColor = avatarColor;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
