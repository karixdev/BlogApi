package com.github.karixdev.blogapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationExceptionResponse {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;

    @Builder.Default
    private Map<String, String> constraints = new HashMap<>();

}
