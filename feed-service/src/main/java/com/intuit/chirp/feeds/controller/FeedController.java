package com.intuit.chirp.feeds.controller;

import com.intuit.chirp.feeds.model.dto.FeedResponse;
import com.intuit.chirp.feeds.service.FeedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/feeds")
public class FeedController {

    private final FeedService feedService;

    @GetMapping("")
    public FeedResponse getFeedPaginated(@Valid @RequestParam(required = false)
                                         UUID token, Principal principal) {
        return feedService.fetchLatestUserFeed(principal, Optional.ofNullable(token));
    }

}
