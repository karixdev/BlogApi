package com.github.karixdev.blogapi.validation;

import com.github.karixdev.blogapi.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class ExistingBlogPostValidator implements ConstraintValidator<ExistingBlogPost, Long> {

    private final BlogPostRepository blogPostRepository;

    @Override
    public boolean isValid(Long blogPostId, ConstraintValidatorContext constraintValidatorContext) {
        return blogPostRepository.findById(blogPostId)
                .isPresent();
    }
}
