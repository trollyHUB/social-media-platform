package com.tolegen.webapplicationdevelopmentlab2.service;

import com.tolegen.webapplicationdevelopmentlab2.config.JwtUtil;
import com.tolegen.webapplicationdevelopmentlab2.dto.AuthResponse;
import com.tolegen.webapplicationdevelopmentlab2.dto.LoginRequest;
import com.tolegen.webapplicationdevelopmentlab2.dto.RegisterRequest;
import com.tolegen.webapplicationdevelopmentlab2.model.User;
import com.tolegen.webapplicationdevelopmentlab2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByUsernameIgnoreCase(req.getUsername())) {
            throw new RuntimeException("Username уже занят: " + req.getUsername());
        }
        if (userRepository.existsByEmailIgnoreCase(req.getEmail())) {
            throw new RuntimeException("Email уже зарегистрирован: " + req.getEmail());
        }

        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail().toLowerCase())
                .password(passwordEncoder.encode(req.getPassword()))
                .bio(req.getBio() != null ? req.getBio() : "Новый пользователь NEXIS 👋")
                .role(User.Role.USER)
                .enabled(true)
                .build();

        userRepository.save(user);
        log.info("✅ Новый пользователь зарегистрирован: {}", user.getUsername());

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return AuthResponse.builder()
                .token(token)
                .user(AuthResponse.UserDto.from(user))
                .build();
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmailIgnoreCase(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Пользователь с таким email не найден"));

        if (!user.getEnabled()) {
            throw new RuntimeException("Аккаунт заблокирован");
        }

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Неверный пароль");
        }

        log.info("✅ Вход: {}", user.getUsername());
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return AuthResponse.builder()
                .token(token)
                .user(AuthResponse.UserDto.from(user))
                .build();
    }

    public AuthResponse.UserDto me(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return AuthResponse.UserDto.from(user);
    }
}
