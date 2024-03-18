package com.intuit.chirp.feeds.service;

import com.intuit.chirp.feeds.exception.BadRequestException;
import com.intuit.chirp.feeds.model.dto.FeedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.annotation.DirtiesContext;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
@RequiredArgsConstructor
@Slf4j
public class FeedServiceTest {

    @Autowired
    private FeedService feedService;
    @Mock
    Principal principal;
    @MockBean
    private JwtDecoder jwtDecoder;


    @Test
    public void givenValidPrincipleAndEmptyAndValidTokenWhenQueriedFetchLatestFeedThenExpectValidFeedResponse() {
        when(principal.getName()).thenReturn("ben");
        FeedResponse feedResponse = feedService.fetchLatestUserFeed(principal, Optional.empty());
        Assertions.assertNotNull(feedResponse);
        assertFalse(feedResponse.tweets().isEmpty());
        int FEED_SIZE = 10;
        assertTrue(feedResponse.tweets().size() <= FEED_SIZE);
        Assertions.assertNotNull(feedResponse.nextToken());
        UUID nextToken = feedResponse.nextToken();
        feedResponse = feedService.fetchLatestUserFeed(principal, Optional.of(nextToken));
        Assertions.assertNotNull(feedResponse);
        assertFalse(feedResponse.tweets().isEmpty());
        assertTrue(feedResponse.tweets().size() < FEED_SIZE);
    }

    @Test
    public void givenValidPrincipleAndInvalidTokenWhenQueriedFetchLatestFeedThenExpectValidFeedResponse() {
        when(principal.getName()).thenReturn("ben");
        Assertions.assertThrows(BadRequestException.class, () -> feedService.fetchLatestUserFeed(principal, Optional.of(UUID.randomUUID())));
    }


    @Test
    public void givenValidPrincipleAndEmptyTokenWhenQueriedFetchLatestFeedThenExpectEmptyFeedResponse() {
        when(principal.getName()).thenReturn("jerry");
        FeedResponse feedResponse = feedService.fetchLatestUserFeed(principal, Optional.empty());
        Assertions.assertNotNull(feedResponse);
        assertEquals(0, feedResponse.tweets().size());
    }

    @Test
    public void givenValidPrincipleAndEmptyTokenAndInactiveFollowingWhenQueriedFetchLatestFeedThenExpectEmptyFeedResponse() {
        when(principal.getName()).thenReturn("bob");
        FeedResponse feedResponse = feedService.fetchLatestUserFeed(principal, Optional.empty());
        Assertions.assertNotNull(feedResponse);
        assertEquals(0, feedResponse.tweets().size());
    }
}
