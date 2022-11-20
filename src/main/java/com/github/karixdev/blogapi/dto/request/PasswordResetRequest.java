package com.github.karixdev.blogapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequest {

    @NotBlank
    @Size(min = 8, max = 255)
    private String password;

}
