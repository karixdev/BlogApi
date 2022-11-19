package com.github.karixdev.blogapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.karixdev.blogapi.entity.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {

    @JsonProperty("vote")
    public VoteType voteType;

}
