package com.intuit.chirp.tweets.controller;

import com.intuit.chirp.tweets.exception.BadRequestException;
import com.intuit.chirp.tweets.model.domain.Following;
import com.intuit.chirp.tweets.model.dto.FollowRequest;
import com.intuit.chirp.tweets.model.dto.FollowResponse;
import com.intuit.chirp.tweets.service.FollowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/following")
public class FollowController {

    private final FollowService followService;

    @GetMapping
    public FollowResponse getFollowing(Principal principal) {
        return new FollowResponse(followService.getAllFollowing(principal));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addFollowing(@Valid @RequestBody FollowRequest followRequest, Principal principal) {
        Optional<Following> following = followService.addFollowing(followRequest, principal);
        return following.map(f -> ResponseEntity.status(HttpStatus.CREATED).build())
                .orElseThrow(() -> new BadRequestException("Invalid request - connection already exists"));


    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFollowing(@Valid @RequestBody FollowRequest followRequest, Principal principal) {
        followService.removeAllFollowing(followRequest, principal);

    }
}
