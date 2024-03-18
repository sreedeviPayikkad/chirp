package com.intuit.chirp.tweets.service;

import com.intuit.chirp.tweets.exception.BadRequestException;
import com.intuit.chirp.tweets.model.domain.Following;
import com.intuit.chirp.tweets.model.domain.User;
import com.intuit.chirp.tweets.model.dto.FollowRequest;
import com.intuit.chirp.tweets.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowService {
    private final UserService userService;
    private final FollowRepository followRepository;

    public Optional<Following> addFollowing(FollowRequest followRequest, Principal principal) {
        User user = getUser(principal);
        if (userService.isValidUser(followRequest.id()) && !this.doesConnectionExist(user.getId(), followRequest.id())) {
            Following following = Following.builder().followingId(followRequest.id()).followerId(user.getId()).createdAt(Timestamp.from(Instant.now())).build();
            Following savedFollow = followRepository.save(following);
            log.debug("added {} following {}", savedFollow.getFollowerId(), savedFollow.getFollowingId());
            return Optional.of(savedFollow);
        }

        return Optional.empty();
    }


    private boolean doesConnectionExist(Long followerId, Long followingId) {
        Optional<Following> following = followRepository.findFollowingByFollowerIdAndFollowingId(followerId, followingId);
        return following.isPresent();
    }


    public List<Long> getAllFollowing(Principal principal) {
        User user = getUser(principal);
        Optional<List<Following>> followingsByFollowerId = followRepository.findFollowingsByFollowerId(user.getId());
        return followingsByFollowerId
                .map(followings -> followings.stream().map(Following::getFollowingId).collect(Collectors.toList()))
                .orElse(Collections.emptyList());

    }

    public void removeAllFollowing(FollowRequest followRequest, Principal principal) {
        User user = getUser(principal);
        if (userService.isValidUser(followRequest.id())) {
            Optional<Following> followingsByFollowerIdAndFollowingId = followRepository.findFollowingByFollowerIdAndFollowingId(user.getId(), followRequest.id());
            log.info("{}", followingsByFollowerIdAndFollowingId);
            followingsByFollowerIdAndFollowingId.ifPresentOrElse(f -> followRepository.deleteById(f.getId()),
                    () -> {
                        throw new BadRequestException("invalid request - you dont follow this id");
                    });
        }

    }

    private User getUser(Principal principal) {
        return userService.getUserByLdapId(principal.getName());
    }


}
