package com.github.karixdev.blogapi.service;

import com.github.karixdev.blogapi.dto.request.BlogPostRequest;
import com.github.karixdev.blogapi.dto.request.VoteRequest;
import com.github.karixdev.blogapi.dto.response.BlogPostResponse;
import com.github.karixdev.blogapi.entity.BlogPost;
import com.github.karixdev.blogapi.entity.BlogPostVote;
import com.github.karixdev.blogapi.entity.User;
import com.github.karixdev.blogapi.exception.ForbiddenActionException;
import com.github.karixdev.blogapi.exception.ResourceNotFoundException;
import com.github.karixdev.blogapi.repository.BlogPostRepository;
import com.github.karixdev.blogapi.repository.BlogPostVoteRepository;
import com.github.karixdev.blogapi.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final BlogPostVoteRepository blogPostVoteRepository;

    public BlogPostResponse create(UserPrincipal userPrincipal, BlogPostRequest request) {

        BlogPost blogPost = BlogPost.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(userPrincipal.getUser())
                .build();

        blogPost = blogPostRepository.save(blogPost);

        return new BlogPostResponse(blogPost);
    }

    public Page<BlogPostResponse> getAllPaginated(Integer page, Integer size) {
        System.out.println(page);
        System.out.println(size);
        PageRequest pageRequest = PageRequest.of(page, size);

        return blogPostRepository.findAllBlogPost(pageRequest)
                .map(BlogPostResponse::new);
    }

    public BlogPostResponse getById(Long id) {
        return new BlogPostResponse(findByIdOrThrowException(id));
    }

    public BlogPostResponse updateBlogPost(
            Long id,
            BlogPostRequest request,
            UserPrincipal userPrincipal
    ) {
        BlogPost blogPost = findByIdOrThrowException(id);

        if (!blogPost.getAuthor().equals(userPrincipal.getUser())) {
            throw new ForbiddenActionException("You can not update posts of which you are not the author");
        }

        blogPost.setTitle(request.getTitle());
        blogPost.setContent(request.getContent());

        blogPostRepository.save(blogPost);

        return new BlogPostResponse(blogPost);
    }

    public Map<String, String> deleteBlogPost(
            Long id,
            UserPrincipal userPrincipal
    ) {
        BlogPost blogPost = findByIdOrThrowException(id);

        if (!blogPost.getAuthor().equals(userPrincipal.getUser())) {
            throw new ForbiddenActionException("You can not update posts of which you are not the author");
        }

        blogPostRepository.delete(blogPost);

        return Map.of("message", "success");
    }

    public BlogPost findByIdOrThrowException(Long id) {
        return blogPostRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException(
                            String.format("Blogpost with id: %d not found", id)
                    );
                });
    }

    public Map<String, String> vote(UserPrincipal userPrincipal, Long id, VoteRequest voteRequest) {
        User user = userPrincipal.getUser();
        BlogPost blogPost = findByIdOrThrowException(id);

        BlogPostVote blogPostVote = blogPost.getVotes()
                .stream()
                .filter(vote -> vote.getUser().equals(user))
                .findFirst()
                .orElse(new BlogPostVote(user, blogPost));

        blogPostVote.setVoteType(voteRequest.getVoteType());

        blogPostVoteRepository.save(blogPostVote);



        return Map.of("message", "success");
    }
}
