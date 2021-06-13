package ru.specialist.java.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.specialist.java.spring.dto.CommentDto;
import ru.specialist.java.spring.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public String create(CommentDto comment) {
        commentService.createComment(comment.getPostId(), comment.getContent());
        return "redirect:/post/" + comment.getPostId();
    }
}
