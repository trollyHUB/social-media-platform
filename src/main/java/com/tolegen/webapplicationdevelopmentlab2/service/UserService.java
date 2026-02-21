package com.tolegen.webapplicationdevelopmentlab2.service;

import com.tolegen.webapplicationdevelopmentlab2.model.User;
import com.tolegen.webapplicationdevelopmentlab2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service для работы с пользователями
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        log.debug("📋 Получение всех пользователей");
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        log.debug("🔍 Поиск пользователя по ID: {}", id);
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        log.debug("👤 Поиск пользователя по username: {}", username);
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public Optional<User> findByEmail(String email) {
        log.debug("📧 Поиск пользователя по email: {}", email);
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Transactional
    public User createUser(String username, String email, String bio) {
        log.info("✏️ Создание пользователя (username={}, email={})", username, email);

        // Проверка уникальности username
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new RuntimeException("Username already exists: " + username);
        }

        // Проверка уникальности email
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new RuntimeException("Email already exists: " + email);
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setBio(bio != null ? bio : "Новый пользователь");

        return userRepository.save(user);
    }

    @Transactional
    public boolean deleteUser(Long id) {
        log.info("🗑️ Удаление пользователя ID={}", id);

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public long getTotalUsersCount() {
        return userRepository.count();
    }
}
