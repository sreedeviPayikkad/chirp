package com.intuit.chirp.tweets.repository;

import com.intuit.chirp.tweets.model.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByLdapId(String ldapId);
}
