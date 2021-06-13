package ru.specialist.java.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.specialist.java.spring.dto.PostDto;
import ru.specialist.java.spring.repository.CommentRepository;
import ru.specialist.java.spring.repository.TagRepository;
import ru.specialist.java.spring.repository.UserRepository;
import ru.specialist.java.spring.service.PostService;

import javax.servlet.ServletContext;

import static ru.specialist.java.spring.util.PostUtils.toDto;

@Controller
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;
    private final ServletContext context;

    @Autowired
    public PostController(PostService postService,
                          UserRepository userRepository,
                          TagRepository tagRepository,
                          CommentRepository commentRepository, ServletContext context) {
        this.postService = postService;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.commentRepository = commentRepository;
        this.context = context;
    }

    @GetMapping("/")
    public String index(ModelMap model,
                        @RequestParam(required = false) String search){
        if (search != null) {
            model.put("title", "Search by");
            model.put("subtitle", search.length() < 20
                    ? search
                    : search.substring(20) + "...");
            model.put("posts", postService.search(search));
        } else {
            model.put("title", "All posts");
            model.put("posts", postService.listAllPosts());
        }

        setCommonParams(model);
        return "blog";
    }

    @GetMapping("/user/{username}")
    public String user(ModelMap model,
                        @PathVariable String username){
        model.put("title", "Posts by");
        model.put("subtitle", username);
        model.put("posts", postService.findByUser(username));

        setCommonParams(model);
        return "blog";
    }

    @GetMapping("/tag/{tagName}")
    public String tag(ModelMap model,
                       @PathVariable String tagName){
        model.put("title", "Posts by tag");
        model.put("subtitle", "#" + tagName);
        model.put("posts", postService.findByTag(tagName));

        setCommonParams(model);
        return "blog";
    }

    @GetMapping("/post/new")
    @PreAuthorize("hasRole('USER')")
    public String postNew(ModelMap modelMap) {
        setCommonParams(modelMap);
        return "post-new";
    }

    @PostMapping("/post/new")
    public String postNew(PostDto postDto) {
        postService.createPost(postDto);
        return "redirect:/";
    }

    @GetMapping("/post/{postId}/edit")
    public String postEdit(ModelMap modelMap,
                           @PathVariable long postId) {
        postService.checkAuthority(postId);
        modelMap.put("post", toDto(postService.findById(postId)));
        setCommonParams(modelMap);
        return "post-edit";
    }

    @PostMapping("/post/edit")
    public String postEdit(PostDto postDto) {
        postService.update(postDto);
        return "redirect:/";
    }


    @GetMapping("/post/{id}")
    public String post(@PathVariable long id,
                       ModelMap modelMap){
        modelMap.put("post", postService.findById(id));
        modelMap.put("comments", commentRepository.findByPost_PostId(id));
        setCommonParams(modelMap);
        return "post";
    }


    @PostMapping("/post/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id){
        postService.delete(id);
    }


    private void setCommonParams(ModelMap model){
        model.put("users", userRepository.findAll(Sort.by("username")));
        model.put("tags", tagRepository.findAll(Sort.by("name")));
        model.put("contextPath", context.getContextPath());
    }

}
