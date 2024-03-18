package com.intuit.chirp.tweets.controller;

import com.intuit.chirp.tweets.model.domain.Tweet;
import com.intuit.chirp.tweets.model.dto.TweetRequest;
import com.intuit.chirp.tweets.model.dto.TweetResponse;
import com.intuit.chirp.tweets.model.mapper.TweetMapper;
import com.intuit.chirp.tweets.service.TweetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/tweet")
@RequiredArgsConstructor
public class TweetController {

    private final TweetMapper tweetMapper;
    private final TweetService tweetService;

    @PostMapping
    public TweetResponse save(@Valid @RequestBody TweetRequest tweetRequest, Principal principal) {
        Tweet savedTweet = tweetService.save(tweetMapper.toTweet(tweetRequest), principal);
        return tweetMapper.toTweetResponse(savedTweet);
    }

}
