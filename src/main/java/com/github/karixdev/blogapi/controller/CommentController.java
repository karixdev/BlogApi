package com.github.karixdev.blogapi.controller;

import com.github.karixdev.blogapi.dto.request.CommentRequest;
import com.github.karixdev.blogapi.dto.request.UpdateCommentRequest;
import com.github.karixdev.blogapi.dto.response.CommentResponse;
import com.github.karixdev.blogapi.security.CurrentUser;
import com.github.karixdev.blogapi.security.UserPrincipal;
import com.github.karixdev.blogapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentResponse create(
            @CurrentUser UserPrincipal userPrincipal,
            @Valid @RequestBody CommentRequest commentRequest
    ) {
        return commentService.create(userPrincipal, commentRequest);
    }

    @GetMapping("/blog-post/{id}")
    public Page<CommentResponse> getCommentsByBlogPost(
            @PathVariable(name = "id") Long blogPostId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        return commentService.getCommentsByBlogPost(blogPostId, page, size);
    }

    @PutMapping("/{id}")
    public CommentResponse update(
            @CurrentUser UserPrincipal userPrincipal,
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody UpdateCommentRequest updateCommentRequest
    ) {
        return commentService.update(userPrincipal, id, updateCommentRequest);
    }

}
