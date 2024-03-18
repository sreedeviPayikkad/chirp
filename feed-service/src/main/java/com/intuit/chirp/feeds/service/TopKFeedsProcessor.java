package com.intuit.chirp.feeds.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.chirp.feeds.config.AppConfig;
import com.intuit.chirp.feeds.model.domain.TokenData;
import com.intuit.chirp.feeds.model.domain.Tweet;
import com.intuit.chirp.feeds.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopKFeedsProcessor {

    private final AppConfig appConfig;

    private final TokenRepository tokenRepository;


    public ImmutablePair<List<Tweet>, UUID> getTopFeeds(Map<Long, List<Tweet>> userTweets,
                                                        Map<Long, Timestamp> userTimestampData)
            throws JsonProcessingException {

        final List<Tweet> feedTweets = new ArrayList<>();
        final PriorityQueue<ImmutablePair<Long, Integer>> topTweets = new PriorityQueue<>(((o1, o2) -> userTweets.get(o2.getLeft()).get(o2.getRight()).getCreatedAt().compareTo(
                userTweets.get(o1.getLeft()).get(o1.getRight()).getCreatedAt()
        )));

        userTweets.forEach((k, v) -> {
            if (v.size() > 0)
                topTweets.add(new ImmutablePair<>(k, 0));
        });

        while (feedTweets.size() < appConfig.getFeedSize() && topTweets.size() > 0) {
            ImmutablePair<Long, Integer> top = topTweets.poll();
            Tweet topTweet = userTweets.get(top.getLeft()).get(top.getRight());
            feedTweets.add(topTweet);
            userTimestampData.put(topTweet.getUserId(), topTweet.getCreatedAt());
            int nextIdx = top.getRight() + 1;
            if (userTweets.get(top.getLeft()).size() > nextIdx) {
                topTweets.add(new ImmutablePair<>(top.getLeft(), nextIdx));
            }

        }

        log.debug("{}", feedTweets);
        log.debug("{}", userTimestampData);
        if(feedTweets.size() < appConfig.getFeedSize()) {
            return new ImmutablePair<>(feedTweets, null);
        }
        TokenData nextTokendata = this.updateTokenData(userTimestampData);
        return new ImmutablePair<>(feedTweets, nextTokendata.getId());
    }

    private TokenData updateTokenData(Map<Long, Timestamp> lastProcessedTimestamp) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] jsonBytes = objectMapper.writeValueAsBytes(lastProcessedTimestamp);
        return tokenRepository.save(TokenData.builder().data(jsonBytes).build());

    }


}
