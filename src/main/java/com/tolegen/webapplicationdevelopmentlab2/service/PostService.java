package com.tolegen.webapplicationdevelopmentlab2.service;

import com.tolegen.webapplicationdevelopmentlab2.model.Post;
import com.tolegen.webapplicationdevelopmentlab2.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service для работы с постами
 * Бизнес-логика между Controller и Repository
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public List<Post> getAllPosts() {
        log.debug("📋 Получение всех постов");
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public Optional<Post> getPostById(Long id) {
        log.debug("🔍 Поиск поста по ID: {}", id);
        return postRepository.findById(id);
    }

    public List<Post> getPostsByAuthor(String author) {
        log.debug("👤 Поиск постов автора: {}", author);
        return postRepository.findByAuthorIgnoreCaseOrderByCreatedAtDesc(author);
    }

    @Transactional
    public Post createPost(String author, String content) {
        log.info("✏️ Создание поста (author={})", author);

        Post post = new Post();
        post.setAuthor(author);
        post.setContent(content);
        post.setLikes(0);
        post.setCommentsCount(0);

        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long id, String content) {
        log.info("📝 Обновление поста ID={}", id);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setContent(content);
        return postRepository.save(post);
    }

    @Transactional
    public boolean deletePost(Long id) {
        log.info("🗑️ Удаление поста ID={}", id);

        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Post likePost(Long id) {
        log.info("❤️ Лайк посту ID={}", id);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setLikes(post.getLikes() + 1);
        return postRepository.save(post);
    }

    public long getTotalPostsCount() {
        return postRepository.count();
    }

    public int getTotalLikes() {
        return postRepository.findAll().stream()
                .mapToInt(Post::getLikes)
                .sum();
    }

    public List<Post> getTrendingPosts(int limit) {
        return postRepository.findAllByOrderByLikesDesc()
                .stream()
                .limit(limit)
                .toList();
    }
}
