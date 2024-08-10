package com.backend.socialmedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.socialmedia.entities.Comment;

import java.util.List;



public interface CommentRepository extends JpaRepository<Comment,Long> {


    List<Comment> findByUserIdAndPostId(Long userId, Long postId);

    List<Comment> findByUserId(Long userId);

    List<Comment> findByPostId(Long postId);

    @Query(value = "SELECT c.post_id, u.image, u.username FROM comment c LEFT JOIN user u ON u.id = c.user_id WHERE c.post_id IN (:postIds) LIMIT 5", nativeQuery = true)
    List<Object> findUserCommentsByPostId(@Param("postIds") List<Long> postIds);
}