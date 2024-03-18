package com.intuit.chirp.tweets.validations;

import com.intuit.chirp.tweets.config.AppConfig;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TweetContentValidator implements ConstraintValidator<TweetContent, String> {

    private final AppConfig appConfig;

    @Override
    public void initialize(TweetContent constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.length() >= appConfig.getMinTweetSize() && s.length() <= appConfig.getMaxTweetSize();
    }


}
