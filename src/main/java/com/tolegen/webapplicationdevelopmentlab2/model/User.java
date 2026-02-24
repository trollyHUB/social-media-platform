package com.tolegen.webapplicationdevelopmentlab2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @JsonIgnore
    private String password;

    @Column(length = 500)
    private String bio;

    @Column(name = "avatar_color", length = 100)
    private String avatarColor;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

    @Builder.Default
    private Boolean enabled = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "last_active")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastActive;

    public enum Role {
        USER, MODERATOR, ADMIN
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.lastActive = LocalDateTime.now();
        if (this.avatarColor == null) {
            String[] colors = {"#6c63ff","#11998e","#ee0979","#f093fb","#4facfe","#f7971e","#a18cd1"};
            this.avatarColor = colors[(int)(Math.random() * colors.length)];
        }
        if (this.enabled == null) this.enabled = true;
        if (this.role == null) this.role = Role.USER;
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastActive = LocalDateTime.now();
    }

    public boolean isAdmin() { return role == Role.ADMIN; }
    public boolean isModerator() { return role == Role.MODERATOR || role == Role.ADMIN; }
}
