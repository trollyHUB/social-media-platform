package com.tolegen.webapplicationdevelopmentlab2.dto;

import com.tolegen.webapplicationdevelopmentlab2.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private UserDto user;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDto {
        private Long id;
        private String username;
        private String email;
        private String bio;
        private String avatarColor;
        private String avatarUrl;
        private String role;
        private String createdAt;

        public static UserDto from(User user) {
            return UserDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .bio(user.getBio())
                    .avatarColor(user.getAvatarColor())
                    .avatarUrl(user.getAvatarUrl())
                    .role(user.getRole().name())
                    .createdAt(user.getCreatedAt() != null ? user.getCreatedAt().toString() : null)
                    .build();
        }
    }
}
