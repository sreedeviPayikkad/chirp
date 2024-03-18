package com.intuit.chirp.tweets.model.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Data
@Builder
@Table("tweet")
public class Tweet {
    @Id
    private Long id;

    @Column("user_id")
    private Long userId;

    private String content;

    @Column("created_at")
    private Timestamp createdAt;
}