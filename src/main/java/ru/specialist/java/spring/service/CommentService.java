package ru.specialist.java.spring.service;


import ru.specialist.java.spring.entity.Comment;

import java.util.List;

public interface CommentService {

    long createComment(Long postId, String content);

    List<Comment> findByPost(long postId);

}
