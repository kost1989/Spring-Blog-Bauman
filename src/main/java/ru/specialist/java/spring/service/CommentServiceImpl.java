package ru.specialist.java.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.specialist.java.spring.dto.CommentDto;
import ru.specialist.java.spring.entity.Comment;
import ru.specialist.java.spring.entity.User;
import ru.specialist.java.spring.repository.CommentRepository;
import ru.specialist.java.spring.repository.PostRepository;
import ru.specialist.java.spring.repository.UserRepository;
import ru.specialist.java.spring.util.SecurityUtils;

import java.awt.print.PrinterJob;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }



    @Override
    @PreAuthorize("hasRole('USER')")
    public long createComment(Long postId, String content) {
        Comment comment = new Comment();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        comment.setPost(postRepository.findById(postId).orElseThrow());
        comment.setUser(userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username)));
        comment.setContent(content);
        comment.setDtCreated(LocalDateTime.now());
        commentRepository.save(comment);


        return comment.getCommentId();
    }

    @Override
    public List<Comment> findByPost(long postId) {
        return commentRepository.findByPost_PostId(postId);
    }

    public void create(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setPost(postRepository.findById(commentDto.getPostId()).get());
        comment.setContent(commentDto.getContent());
        comment.setUser(new User());
        comment.setDtCreated(LocalDateTime.now());
        commentRepository.save(comment);
    }

}