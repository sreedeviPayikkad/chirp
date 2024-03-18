package com.intuit.chirp.feeds.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class FeedQueryHandler {

    private static final String SINGLE_USER_QUERY_TEMPLATE = """
                (SELECT
                    id, user_id, created_at, content
                FROM
                    tweet
                WHERE
                    user_id = ?
                    AND created_at < ?
                order by
                    created_at desc
                limit 100)
            """;

    private static final String UNION = " UNION ";

    public PreparedStatementCreator prepareQuery(Map<Long, Timestamp> lastUsedIdTimeStampData) {
        List<String> queries = new ArrayList<>();
        AtomicInteger index = new AtomicInteger(1);
        lastUsedIdTimeStampData.forEach((k, v) -> queries.add(SINGLE_USER_QUERY_TEMPLATE));
        String dynamicQuery = String.join(UNION, queries);

        return connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(dynamicQuery);
            lastUsedIdTimeStampData.forEach((k, v) -> {
                try {
                    preparedStatement.setLong(index.getAndIncrement(), k);
                    preparedStatement.setTimestamp(index.getAndIncrement(), v);
                } catch (SQLException e) {
                    log.error("prepare query errored : {}", e.getMessage());
                    throw new RuntimeException(e);
                }
            });
            return preparedStatement;
        };
    }
}