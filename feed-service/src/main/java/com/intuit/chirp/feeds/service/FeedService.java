package com.intuit.chirp.feeds.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.chirp.feeds.exception.BadRequestException;
import com.intuit.chirp.feeds.model.domain.Tweet;
import com.intuit.chirp.feeds.model.dto.FeedResponse;
import com.intuit.chirp.feeds.model.dto.TweetResponse;
import com.intuit.chirp.feeds.model.mapper.TweetMapper;
import com.intuit.chirp.feeds.repository.FollowingRepository;
import com.intuit.chirp.feeds.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final FollowingRepository followingRepository;
    private final TweetMapper tweetMapper;
    private final TopKFeedsProcessor topKFeedsProcessor;
    private final TokenRepository tokenRepository;
    private final ObjectMapper objectMapper;
    private final JdbcTemplate jdbcTemplate;
    private final FeedQueryHandler feedQueryHandler;
    private final UserService userService;


    public FeedResponse fetchLatestUserFeed(Principal principal, Optional<UUID> token) {
        try {
            long userId = userService.getUserByLdapId(principal.getName()).getId();

            //get all users the current user follows.
            List<Long> followedIds = getAllFollowers(userId);
            log.debug("{}", followedIds);
            if (followedIds.size() == 0) {
                return FeedResponse.builder().tweets(new ArrayList<>()).build();
            }


            //get map of following vs latest timestamp process, if there is a valid token present in DB
            Optional<Map<Long, Timestamp>> lastProcessedTimestamp = token.map(
                    this::getLastProcessedUserTimestampMapFromToken
            ).orElseGet(() -> {
                Map<Long, Timestamp> defaultMap = new HashMap<>();
                Timestamp now = Timestamp.from(Instant.now());
                followedIds.forEach(fId -> defaultMap.put(fId, now));
                return Optional.of(defaultMap);

            });

            if (lastProcessedTimestamp.isEmpty()) return FeedResponse.builder().tweets(new ArrayList<>()).build();

            Map<Long, List<Tweet>> userTweets = getAllTweets(lastProcessedTimestamp.get());
            log.debug("{}", userTweets);
            if (userTweets.size() == 0) {
                return FeedResponse.builder().tweets(new ArrayList<>()).build();
            }
            ImmutablePair<List<Tweet>, UUID> topFeedData = topKFeedsProcessor.getTopFeeds(userTweets, lastProcessedTimestamp.get());
            List<TweetResponse> topTweets = topFeedData.left.stream().map(tweetMapper::toTweetResponse).collect(Collectors.toList());

            //delete the used token
            token.ifPresent(this::deleteLastUsedToken);
            return FeedResponse.builder().tweets(topTweets).nextToken(topFeedData.getRight()).build();
        } catch (IOException e) {
            log.error("IOException while getting top feeds : ", e);
            throw new RuntimeException(e);
        }
    }

    private Map<Long, List<Tweet>> getAllTweets(Map<Long, Timestamp> lastProcessedTimestamp) {
        return jdbcTemplate.query(feedQueryHandler.prepareQuery(lastProcessedTimestamp), (resultSet, rowNum) -> {
                    long userId = resultSet.getLong("user_id");
                    long id = resultSet.getLong("id");
                    Timestamp createdAt = resultSet.getTimestamp("created_at");
                    String content = resultSet.getString("content");
                    return Tweet.builder().id(id).userId(userId).content(content).createdAt(createdAt).build();
                }).stream()
                .collect(Collectors.groupingBy(Tweet::getUserId));
    }

    private void deleteLastUsedToken(UUID token) {
        tokenRepository.deleteById(token);
        log.debug("token deleted");
    }

    private Optional<Map<Long, Timestamp>> getLastProcessedUserTimestampMapFromToken(UUID token) {
        return Optional.of(tokenRepository.findById(token)
                .map(tokenData -> {
                    try {
                        Map<Long, Timestamp> lastProcessedTimestamp = objectMapper.readValue(tokenData.getData(), new TypeReference<>() {
                        });
                        log.debug("{}", lastProcessedTimestamp);
                        return lastProcessedTimestamp;
                    } catch (IOException e) {
                        log.error("IOException while fetching token data");
                        throw new RuntimeException(e);
                    }
                })
                .orElseGet(() -> {
                    log.error("{expired token}");
                    throw new BadRequestException("expired token");
                }));
    }


    private List<Long> getAllFollowers(long userId) {
        return new ArrayList<>(followingRepository.findAllFollowing(userId));

    }

}
