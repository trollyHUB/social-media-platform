package com.tolegen.webapplicationdevelopmentlab2.repository;

import com.tolegen.webapplicationdevelopmentlab2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA Repository для пользователей
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // SELECT * FROM users WHERE LOWER(username) = LOWER(?)
    Optional<User> findByUsernameIgnoreCase(String username);

    // SELECT * FROM users WHERE LOWER(email) = LOWER(?)
    Optional<User> findByEmailIgnoreCase(String email);

    // SELECT EXISTS(SELECT 1 FROM users WHERE LOWER(username) = LOWER(?))
    boolean existsByUsernameIgnoreCase(String username);

    // SELECT EXISTS(SELECT 1 FROM users WHERE LOWER(email) = LOWER(?))
    boolean existsByEmailIgnoreCase(String email);
}
