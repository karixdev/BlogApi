package com.github.karixdev.blogapi.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.karixdev.blogapi.entity.BlogPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogPostResponse {

    private Long id;
    private String title;
    private String content;

    @JsonIgnoreProperties({ "is_enabled", "role", "email" })
    private UserResponse author;

    public BlogPostResponse(BlogPost blogPost) {
        this.id = blogPost.getId();
        this.title = blogPost.getTitle();
        this.author = new UserResponse(blogPost.getAuthor());
    }
}
