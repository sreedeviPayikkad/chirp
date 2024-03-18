package com.intuit.chirp.feeds.repository;

import com.intuit.chirp.feeds.model.domain.TokenData;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TokenRepository extends CrudRepository<TokenData, UUID> {
    void deleteById(UUID token);
}
