package com.github.karixdev.blogapi.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.karixdev.blogapi.entity.Comment;
import com.github.karixdev.blogapi.entity.CommentVote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {

    private Long id;
    private String content;

    @JsonIgnoreProperties({"email", "is_enabled", "role"})
    private UserResponse author;

    @JsonProperty("votes")
    private VoteCountResponse<CommentVote> voteCountResponse;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = new UserResponse(comment.getAuthor());
        this.voteCountResponse = new VoteCountResponse<>(comment.getVotes());
    }
}
