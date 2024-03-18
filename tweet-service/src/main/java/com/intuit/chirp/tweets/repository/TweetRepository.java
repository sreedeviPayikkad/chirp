package com.intuit.chirp.tweets.repository;

import org.springframework.data.repository.CrudRepository;

public interface TweetRepository extends CrudRepository<com.intuit.chirp.tweets.model.domain.Tweet, Long> {
}
