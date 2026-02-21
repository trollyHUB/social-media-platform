package com.tolegen.webapplicationdevelopmentlab2.service;

import com.tolegen.webapplicationdevelopmentlab2.model.Comment;
import com.tolegen.webapplicationdevelopmentlab2.model.Post;
import com.tolegen.webapplicationdevelopmentlab2.repository.CommentRepository;
import com.tolegen.webapplicationdevelopmentlab2.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service для работы с комментариями
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public List<Comment> getCommentsByPostId(Long postId) {
        log.debug("💬 Получение комментариев для поста ID={}", postId);
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
    }

    @Transactional
    public Comment addComment(Long postId, String author, String content) {
        log.info("✏️ Добавление комментария к посту ID={} от {}", postId, author);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found: " + postId));

        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setAuthor(author);
        comment.setContent(content);

        Comment saved = commentRepository.save(comment);

        // Обновляем счётчик комментариев в посте
        post.setCommentsCount(post.getCommentsCount() + 1);
        postRepository.save(post);

        return saved;
    }

    @Transactional
    public boolean deleteComment(Long commentId) {
        log.info("🗑️ Удаление комментария ID={}", commentId);

        if (commentRepository.existsById(commentId)) {
            Comment comment = commentRepository.findById(commentId).orElse(null);
            if (comment != null) {
                // Уменьшаем счётчик комментариев
                postRepository.findById(comment.getPostId()).ifPresent(post -> {
                    post.setCommentsCount(Math.max(0, post.getCommentsCount() - 1));
                    postRepository.save(post);
                });
            }
            commentRepository.deleteById(commentId);
            return true;
        }
        return false;
    }

    public long getTotalCommentsCount() {
        return commentRepository.count();
    }
}
