package com.github.karixdev.blogapi.service;

import com.github.karixdev.blogapi.dto.request.CommentRequest;
import com.github.karixdev.blogapi.dto.request.UpdateCommentRequest;
import com.github.karixdev.blogapi.dto.response.CommentResponse;
import com.github.karixdev.blogapi.entity.BlogPost;
import com.github.karixdev.blogapi.entity.Comment;
import com.github.karixdev.blogapi.entity.User;
import com.github.karixdev.blogapi.entity.UserRole;
import com.github.karixdev.blogapi.exception.ForbiddenActionException;
import com.github.karixdev.blogapi.exception.ResourceNotFoundException;
import com.github.karixdev.blogapi.repository.CommentRepository;
import com.github.karixdev.blogapi.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

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

        commentRepository.save(comment);

        return new CommentResponse(comment);
    }

    public Page<CommentResponse> getCommentsByBlogPost(Long blogPostId, Integer page, Integer size) {
        blogPostService.findByIdOrThrowException(blogPostId);

        PageRequest pageRequest = PageRequest.of(page, size);

        return commentRepository.findByBlogPostId(blogPostId, pageRequest)
                .map(CommentResponse::new);
    }

    public CommentResponse update(UserPrincipal userPrincipal, Long id, UpdateCommentRequest updateCommentRequest) {
        Comment comment = findByIdOrThrowException(id);

        if (!comment.getAuthor().equals(userPrincipal.getUser())) {
            throw new ForbiddenActionException("You can not update comment of which you are not the author");
        }

        comment.setContent(updateCommentRequest.getContent());
        commentRepository.save(comment);

        return new CommentResponse(comment);
    }

    public Map<String, String> delete(UserPrincipal userPrincipal, Long id) {
        Comment comment = findByIdOrThrowException(id);

        User currentUser = userPrincipal.getUser();
        User author = comment.getAuthor();

        boolean isCurrentUserAnAuthor = author.equals(currentUser);

        if (!isCurrentUserAnAuthor &&
                currentUser.getUserRole() != UserRole.ROLE_ADMIN) {
            throw new ForbiddenActionException("You can not delete comment of which you are not the author");
        }

        if (userService.isUserAnAdmin(currentUser) && userService.isUserAnAdmin(author) && !isCurrentUserAnAuthor) {
            throw new ForbiddenActionException("You can not delete comment of which you are not the author");
        }

        commentRepository.delete(comment);

        return Map.of("message", "success");
    }

    public Comment findByIdOrThrowException(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException(
                            String.format("Comment with id: %d not found", id)
                    );
                });
    }
}
