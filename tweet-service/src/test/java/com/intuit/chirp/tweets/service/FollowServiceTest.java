/*

package com.intuit.chirp.tweets.service;

import com.intuit.chirp.tweets.model.domain.Following;
import com.intuit.chirp.tweets.model.dto.FollowRequest;
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

import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor
@Slf4j
public class FollowServiceTest {


    @Autowired
    private FollowService followService;

    @Mock
    Principal principal;

    @MockBean
    JwtDecoder jwtDecoder;


    @Test
    public void testAddFollowing_GivenValidInput_WhenAddConnection_ThenExpectCreatedFollowing() {
        FollowRequest validFollowRequest = FollowRequest.builder().id(1L).build();
        when(principal.getName()).thenReturn("ben");
        Optional<Following> following = followService.addFollowing(validFollowRequest, principal);
        Assertions.assertTrue(following.isPresent());
    }
}

*/
