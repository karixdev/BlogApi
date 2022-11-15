package com.github.karixdev.blogapi.service;

import com.github.karixdev.blogapi.dto.request.BlogPostRequest;
import com.github.karixdev.blogapi.dto.response.BlogPostResponse;
import com.github.karixdev.blogapi.entity.BlogPost;
import com.github.karixdev.blogapi.exception.ResourceNotFoundException;
import com.github.karixdev.blogapi.repository.BlogPostRepository;
import com.github.karixdev.blogapi.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final UserService userService;

    public BlogPostResponse create(UserPrincipal userPrincipal, BlogPostRequest request) {

        BlogPost blogPost = BlogPost.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(userPrincipal.getUser())
                .build();

        blogPost = blogPostRepository.save(blogPost);

        return mapBlogPostToBlogPostResponse(blogPost);
    }

    public Page<BlogPostResponse> getAllPaginated(Integer page, Integer size) {
        System.out.println(page);
        System.out.println(size);
        PageRequest pageRequest = PageRequest.of(page, size);

        return blogPostRepository.findAllBlogPost(pageRequest)
                .map(this::mapBlogPostToBlogPostResponse);
    }

    public BlogPostResponse getById(Long id) {
        return mapBlogPostToBlogPostResponse(findByIdOrThrowException(id));
    }

    public BlogPost findByIdOrThrowException(Long id) {
        return blogPostRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException(
                            String.format("Blogpost with id: %d not found", id)
                    );
                });
    }

    private BlogPostResponse mapBlogPostToBlogPostResponse(BlogPost blogPost) {
        return BlogPostResponse.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .content(blogPost.getContent())
                .author(userService.mapUserToUserResponse(blogPost.getAuthor()))
                .build();
    }

}
