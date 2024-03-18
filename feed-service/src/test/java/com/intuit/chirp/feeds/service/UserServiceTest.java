package com.intuit.chirp.feeds.service;

import com.intuit.chirp.feeds.model.domain.User;
import com.intuit.chirp.feeds.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private final String VALID_ID = "valid";
    private User VALID_USER;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        VALID_USER = User.builder().id(1L).fistName("valid").lastName("user").email("validuser.gmail.com").ldapId(VALID_ID).build();
        Mockito.when(userRepository.findByLdapId(VALID_ID)).thenReturn(VALID_USER);

    }

    @Test
    public void givenValidIdWhenQueriedGetUserByLdapIdThenGetExpectedUser() {
        User userByLdapId = userService.getUserByLdapId(VALID_ID);
        Assertions.assertEquals(userByLdapId, VALID_USER);
    }


}
