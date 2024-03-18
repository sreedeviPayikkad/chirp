package com.intuit.chirp.feeds.model.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Data
@Builder
@Table(name = "following")
public class Following {

    @Id
    private Long id;

    @Column("follower_id")
    private Long userId;

    @Column("following_id")
    private Long followeeId;

    @Column("created_at")
    private Timestamp createdAt;
}
