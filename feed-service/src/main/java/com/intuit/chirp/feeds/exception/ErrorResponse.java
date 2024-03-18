package com.intuit.chirp.feeds.exception;

import java.util.List;

public record ErrorResponse<T>(List<T> errors) {
}
