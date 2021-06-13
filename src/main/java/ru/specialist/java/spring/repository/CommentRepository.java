package ru.specialist.java.spring.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.specialist.java.spring.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost_PostIdAndUser_UserId(long postId, long userId);
    List<Comment> findByPost_PostId(long postId);
}
