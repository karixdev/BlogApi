package com.github.karixdev.blogapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.karixdev.blogapi.entity.BaseVote;
import com.github.karixdev.blogapi.entity.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteCountResponse<T extends BaseVote> {

    @JsonProperty("down")
    private Long downVotesCount;

    @JsonProperty("up")
    private Long upVotesCount;

    public VoteCountResponse(Set<T> votes) {
        this.downVotesCount = votes.stream()
                .filter(vote -> vote.getVoteType() == VoteType.DOWN)
                .count();

        this.upVotesCount = votes.stream()
                .filter(vote -> vote.getVoteType() == VoteType.UP)
                .count();
    }
}
