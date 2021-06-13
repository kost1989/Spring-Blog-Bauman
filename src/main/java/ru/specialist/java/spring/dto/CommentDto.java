package ru.specialist.java.spring.dto;

public class CommentDto {

    private long postId;

    private String content;

    public CommentDto(long postId, String content) {
        this.postId = postId;
        this.content = content;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
