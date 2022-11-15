package com.github.karixdev.blogapi.controller;

import com.github.karixdev.blogapi.dto.request.BlogPostRequest;
import com.github.karixdev.blogapi.dto.response.BlogPostResponse;
import com.github.karixdev.blogapi.security.CurrentUser;
import com.github.karixdev.blogapi.security.UserPrincipal;
import com.github.karixdev.blogapi.service.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/blog-post")
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostService blogPostService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public BlogPostResponse create(
            @CurrentUser UserPrincipal userPrincipal,
            @Valid @RequestBody BlogPostRequest blogPostRequest
    ) {
        return blogPostService.create(userPrincipal, blogPostRequest);
    }

}
