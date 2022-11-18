package com.github.karixdev.blogapi.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.karixdev.blogapi.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {

    private Long id;
    private String content;

    @JsonIgnoreProperties({"email", "is_enabled", "role"})
    private UserResponse author;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = new UserResponse(comment.getAuthor());
    }
}
