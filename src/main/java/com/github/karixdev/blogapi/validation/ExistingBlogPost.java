package com.github.karixdev.blogapi.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistingBlogPostValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistingBlogPost {
    String message() default "Blog post with provided id not found";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
