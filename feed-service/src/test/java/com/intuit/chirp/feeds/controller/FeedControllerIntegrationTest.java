package com.intuit.chirp.feeds.controller;

import com.intuit.chirp.feeds.model.dto.FeedResponse;
import com.intuit.chirp.feeds.model.dto.TweetResponse;
import com.intuit.chirp.feeds.service.FeedService;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class FeedControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedService feedService;


    public FeedControllerIntegrationTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFeedPaginated_WhenUnauthenticatedThen401() throws Exception {
        this.mockMvc.perform(get("/api/v1/feeds"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testGetFeedPaginated_WhenValidInput_ShouldReturnValidFeed() throws Exception {
        when(feedService.fetchLatestUserFeed(any(), any())).thenReturn(FeedResponse.builder().tweets(Arrays.asList(TweetResponse.builder().build())).build());
        UUID token = UUID.randomUUID();
        mockMvc.perform(
                        get("/api/v1/feeds")
                                .queryParam("token", String.valueOf(token))
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    void testGetFeedPaginated_WhenInValidInput_ShouldReturnClientError() throws Exception {

        mockMvc.perform(
                        get("/api/v1/feeds")
                                .queryParam("token", "invaliduuid")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


}

