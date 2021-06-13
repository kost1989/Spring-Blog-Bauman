package ru.specialist.java.spring.service;

import ru.specialist.java.spring.dto.PostDto;
import ru.specialist.java.spring.entity.Post;

import java.util.List;

public interface PostService {

    List<Post> listAllPosts();

    List<Post> search(String key);

    List<Post> findByUser(String username);

    List<Post> findByTag(String tagName);

    long createPost(PostDto postDto);

    void checkAuthority(long postId);

    void update(PostDto postDto);

    void delete(long postId);

    Post findById(long postId);
}
