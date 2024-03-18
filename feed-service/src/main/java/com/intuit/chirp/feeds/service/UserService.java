package com.intuit.chirp.feeds.service;

import com.intuit.chirp.feeds.model.domain.User;
import com.intuit.chirp.feeds.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
}
