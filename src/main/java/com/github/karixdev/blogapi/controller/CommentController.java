package com.github.karixdev.blogapi.controller;

import com.github.karixdev.blogapi.dto.request.CommentRequest;
import com.github.karixdev.blogapi.dto.request.UpdateCommentRequest;
import com.github.karixdev.blogapi.dto.request.VoteRequest;
import com.github.karixdev.blogapi.dto.response.CommentResponse;
import com.github.karixdev.blogapi.security.CurrentUser;
import com.github.karixdev.blogapi.security.UserPrincipal;
import com.github.karixdev.blogapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> create(
            @CurrentUser UserPrincipal userPrincipal,
            @Valid @RequestBody CommentRequest commentRequest
    ) {
        return new ResponseEntity<>(
                commentService.create(userPrincipal, commentRequest),
                HttpStatus.CREATED
        );
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

    @DeleteMapping("/{id}")
    public Map<String, String> delete(
            @CurrentUser UserPrincipal userPrincipal,
            @PathVariable(name = "id") Long id
    ) {
        return commentService.delete(userPrincipal, id);
    }

    @PostMapping("/{id}/vote")
    public Map<String, String> vote(
            @CurrentUser UserPrincipal userPrincipal,
            @PathVariable(name = "id") Long id,
            @RequestBody VoteRequest voteRequest
    ) {
        return commentService.vote(userPrincipal, id, voteRequest);
    }
}
