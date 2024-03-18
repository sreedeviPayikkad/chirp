package com.intuit.chirp.tweets.service;

import com.intuit.chirp.tweets.exception.UserNotFoundException;
import com.intuit.chirp.tweets.model.domain.User;
import com.intuit.chirp.tweets.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Cacheable(value = "userCache")
    public User getUserByLdapId(String ldapId) {
        log.debug("fetching user info from DB");
        return userRepository.findByLdapId(ldapId);
    }

    public boolean isValidUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(u -> true).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }
}
