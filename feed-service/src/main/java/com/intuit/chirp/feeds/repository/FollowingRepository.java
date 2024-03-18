package com.intuit.chirp.feeds.repository;

import com.intuit.chirp.feeds.model.domain.Following;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowingRepository extends CrudRepository<Following, Long> {
    @Query(value = "SELECT following_id FROM following WHERE follower_id = :userId")
    List<Long> findAllFollowing(@Param("userId") Long userId);


}
