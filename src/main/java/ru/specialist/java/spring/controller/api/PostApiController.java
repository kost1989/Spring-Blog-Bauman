package ru.specialist.java.spring.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.specialist.java.spring.dto.PostDto;
import ru.specialist.java.spring.service.PostService;
import ru.specialist.java.spring.util.PostUtils;

import java.util.List;
import java.util.stream.Collectors;

import static ru.specialist.java.spring.util.PostUtils.toDto;

@RestController
@RequestMapping("/api/post")
public class PostApiController {

    private final PostService postService;

    @Autowired
    public PostApiController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public ResponseEntity<List<PostDto>> getAll(){
        return new ResponseEntity<>(postService.listAllPosts()
                .stream()
                .map(PostUtils::toDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> get(@PathVariable long postId){
        return new ResponseEntity<>(toDto(postService.findById(postId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.OK);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody PostDto postDto){
        postService.update(postDto);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long postId){
        postService.delete(postId);
    }

}
