package com.intuit.chirp.feeds.model.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "token_data")
@Data
@Builder
public class TokenData {
    @Id
    private UUID id;

    @Column("data")
    private byte[] data;

}
