package com.github.karixdev.blogapi.exception;

import com.github.karixdev.blogapi.dto.response.ValidationExceptionResponse;
import com.github.karixdev.blogapi.util.NameCaseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

    private final NameCaseConverter nameCaseConverter;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponse> methodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        Map<String, String> constraintsMap = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .filter(error -> error.getDefaultMessage() != null)
                .collect(Collectors.toMap(
                        fieldError -> nameCaseConverter.camelToSnake(fieldError.getField()),
                        FieldError::getDefaultMessage));

        var exceptionResponse = ValidationExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(request.getRequestURI())
                .constraints(constraintsMap)
                .build();

        return ResponseEntity.badRequest()
                .body(exceptionResponse);
    }

}
