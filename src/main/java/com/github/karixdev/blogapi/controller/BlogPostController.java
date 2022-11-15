package com.github.karixdev.blogapi.controller;

import com.github.karixdev.blogapi.dto.request.BlogPostRequest;
import com.github.karixdev.blogapi.dto.response.BlogPostResponse;
import com.github.karixdev.blogapi.security.CurrentUser;
import com.github.karixdev.blogapi.security.UserPrincipal;
import com.github.karixdev.blogapi.service.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/collection")
    public Page<BlogPostResponse> getAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size
    ) {
        return blogPostService.getAllPaginated(page, size);
    }

    @GetMapping
    public BlogPostResponse getById(
            @RequestParam(name = "id") Long id
    ) {
        return blogPostService.getById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public BlogPostResponse update(
            @RequestParam(name = "id") Long id,
            @Valid @RequestBody BlogPostRequest blogPostRequest,
            @CurrentUser UserPrincipal userPrincipal
    ) {
        return blogPostService.updateBlogPost(id, blogPostRequest, userPrincipal);
    }

}
