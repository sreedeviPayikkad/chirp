package com.intuit.chirp.tweets.exception;

import java.util.List;

public record ErrorResponse<T>(List<T> errors) {
}
