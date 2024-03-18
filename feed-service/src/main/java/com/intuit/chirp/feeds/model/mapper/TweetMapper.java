package com.intuit.chirp.feeds.model.mapper;

import com.intuit.chirp.feeds.model.domain.Tweet;
import com.intuit.chirp.feeds.model.dto.TweetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TweetMapper {

    TweetResponse toTweetResponse(Tweet tweet);
}
