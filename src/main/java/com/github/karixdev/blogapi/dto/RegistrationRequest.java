package com.github.karixdev.blogapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.karixdev.blogapi.validation.UniqueEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequest {
    @JsonProperty("email")
    @Email
    @UniqueEmail
    @NotBlank
    private String email;

    @JsonProperty("first_name")
    @Size(min = 3, max = 255)
    @NotBlank
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("password")
    @Size(min = 8, max = 255)
    @NotBlank
    private String plainPassword;
}
