package com.intuit.chirp.tweets.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = TweetContentValidator.class)
@Documented
public @interface TweetContent {
    String message() default "invalid tweet content size";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
