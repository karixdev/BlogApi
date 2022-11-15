package com.github.karixdev.blogapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.karixdev.blogapi.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("is_enabled")
    private Boolean isEnabled;

    @JsonProperty("role")
    private UserRole role;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

}
