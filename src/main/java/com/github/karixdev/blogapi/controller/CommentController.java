package com.github.karixdev.blogapi.controller;

import com.github.karixdev.blogapi.dto.request.CommentRequest;
import com.github.karixdev.blogapi.dto.response.CommentResponse;
import com.github.karixdev.blogapi.security.CurrentUser;
import com.github.karixdev.blogapi.security.UserPrincipal;
import com.github.karixdev.blogapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
