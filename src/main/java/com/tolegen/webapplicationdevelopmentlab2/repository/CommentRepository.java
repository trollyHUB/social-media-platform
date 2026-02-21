package com.tolegen.webapplicationdevelopmentlab2.repository;

import com.tolegen.webapplicationdevelopmentlab2.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA Repository для комментариев
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // SELECT * FROM comments WHERE post_id = ? ORDER BY created_at DESC
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);

    // Количество комментариев по посту
    long countByPostId(Long postId);

    // Удалить все комментарии поста
    void deleteByPostId(Long postId);
}
