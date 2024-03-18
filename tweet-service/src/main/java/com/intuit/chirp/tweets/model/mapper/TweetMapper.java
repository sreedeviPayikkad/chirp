package com.intuit.chirp.tweets.model.mapper;

import com.intuit.chirp.tweets.model.domain.Tweet;
import com.intuit.chirp.tweets.model.dto.TweetRequest;
import com.intuit.chirp.tweets.model.dto.TweetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TweetMapper {

    @Mapping(target = "userId", ignore = true)
    Tweet toTweet(TweetRequest tweetRequest);

    TweetResponse toTweetResponse(Tweet tweet);

}
