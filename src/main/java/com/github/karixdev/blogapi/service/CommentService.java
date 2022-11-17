package com.github.karixdev.blogapi.service;

import com.github.karixdev.blogapi.dto.request.CommentRequest;
import com.github.karixdev.blogapi.dto.request.UpdateCommentRequest;
import com.github.karixdev.blogapi.dto.response.CommentResponse;
import com.github.karixdev.blogapi.entity.BlogPost;
import com.github.karixdev.blogapi.entity.Comment;
import com.github.karixdev.blogapi.exception.ForbiddenActionException;
import com.github.karixdev.blogapi.exception.ResourceNotFoundException;
import com.github.karixdev.blogapi.repository.CommentRepository;
import com.github.karixdev.blogapi.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BlogPostService blogPostService;
    private final UserService userService;
    private final CommentRepository commentRepository;

    public CommentResponse create(UserPrincipal userPrincipal, CommentRequest commentRequest) {
        BlogPost blogPost = blogPostService.findByIdOrThrowException(
                commentRequest.getBlogPostId());

        Comment comment = Comment.builder()
                .author(userPrincipal.getUser())
                .blogPost(blogPost)
                .content(commentRequest.getContent())
                .build();

        comment = commentRepository.save(comment);

        return mapCommentToCommentResponse(comment);
    }

    public Page<CommentResponse> getCommentsByBlogPost(Long blogPostId, Integer page, Integer size) {
        blogPostService.findByIdOrThrowException(blogPostId);

        PageRequest pageRequest = PageRequest.of(page, size);

        return commentRepository.findByBlogPostId(blogPostId, pageRequest)
                .map(this::mapCommentToCommentResponse);
    }

    public CommentResponse update(UserPrincipal userPrincipal, Long id, UpdateCommentRequest updateCommentRequest) {
        Comment comment = findByIdOrThrowException(id);

        if (!comment.getAuthor().equals(userPrincipal.getUser())) {
            throw new ForbiddenActionException("You can not update comment of which you are not the author");
        }

        comment.setContent(updateCommentRequest.getContent());
        commentRepository.save(comment);

        return mapCommentToCommentResponse(comment);
    }

    public Comment findByIdOrThrowException(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException(
                            String.format("Comment with id: %d not found", id)
                    );
                });
    }

    public CommentResponse mapCommentToCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(userService.mapUserToUserResponse(comment.getAuthor()))
                .build();
    }
}
