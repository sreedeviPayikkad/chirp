package com.intuit.chirp.feeds.repository;

import com.intuit.chirp.feeds.model.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByLdapId(String ldapId);
}
