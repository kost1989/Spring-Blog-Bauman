package ru.specialist.java.spring.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.specialist.java.spring.dto.CommentDto;
import ru.specialist.java.spring.service.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentApiController {

    private final CommentService commentService;

    @Autowired
    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService
                .createComment(commentDto.getPostId(), commentDto.getContent()), HttpStatus.OK);
    }

}
