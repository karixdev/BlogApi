package com.github.karixdev.blogapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.karixdev.blogapi.validation.constraint.ExistingBlogPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {

    @JsonProperty("content")
    @NotBlank
    @Size(min = 4)
    private String content;

    @JsonProperty("blog_post_id")
    @NotNull
    @ExistingBlogPost
    private Long blogPostId;

}
