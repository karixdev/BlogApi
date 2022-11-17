package com.github.karixdev.blogapi.validation.validator;

import com.github.karixdev.blogapi.repository.BlogPostRepository;
import com.github.karixdev.blogapi.validation.constraint.ExistingBlogPost;
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
