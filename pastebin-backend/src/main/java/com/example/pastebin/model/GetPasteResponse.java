package com.example.pastebin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;

public class GetPasteResponse {
    private String content;

    @JsonProperty("remaining_views")
    private Integer remainingViews;

    @JsonProperty("expires_at")
    private Instant expiresAt;

    public GetPasteResponse(String content, Integer remainingViews, Instant expiresAt) {
        this.content = content;
        this.remainingViews = remainingViews;
        this.expiresAt = expiresAt;
    }

    public String getContent() {
        return content;
    }

    public Integer getRemainingViews() {
        return remainingViews;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}
