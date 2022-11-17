package com.github.karixdev.blogapi.validation.validator;


import com.github.karixdev.blogapi.repository.UserRepository;
import com.github.karixdev.blogapi.validation.constraint.UniqueEmail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository
                .findByEmail(s)
                .isEmpty();
    }
}
