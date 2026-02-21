package com.tolegen.webapplicationdevelopmentlab2.repository;

import com.tolegen.webapplicationdevelopmentlab2.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository для постов
 * Автоматически генерирует SQL запросы из имён методов
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // SELECT * FROM posts ORDER BY created_at DESC
    List<Post> findAllByOrderByCreatedAtDesc();

    // SELECT * FROM posts WHERE LOWER(author) = LOWER(?) ORDER BY created_at DESC
    List<Post> findByAuthorIgnoreCaseOrderByCreatedAtDesc(String author);

    // SELECT * FROM posts ORDER BY likes DESC
    List<Post> findAllByOrderByLikesDesc();
}
