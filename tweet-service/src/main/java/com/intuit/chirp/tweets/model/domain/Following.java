package com.intuit.chirp.tweets.model.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Data
@Builder
@Table("following")
public class Following {
    @Id
    private Long id;

    @Column("follower_id")
    private Long followerId;

    @Column("following_id")
    private Long followingId;

    @Column("created_at")
    private Timestamp createdAt;

}