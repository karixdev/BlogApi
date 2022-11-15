package com.github.karixdev.blogapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("user")
    private UserResponse userResponse;

}
