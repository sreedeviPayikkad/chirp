package com.intuit.chirp.tweets.repository;

import com.intuit.chirp.tweets.model.domain.Following;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends CrudRepository<Following, Long> {
    Optional<List<Following>> findFollowingsByFollowerId(Long followerId);

    Optional<Following> findFollowingByFollowerIdAndFollowingId(Long followerId, Long followingId);

}
