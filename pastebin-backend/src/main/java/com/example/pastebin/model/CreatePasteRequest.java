package com.example.pastebin.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePasteRequest {
	@JsonProperty("content")
	private String content;

	@JsonProperty("ttl_seconds")
	private Integer ttlSeconds;

	@JsonProperty("max_views")
	private Integer maxViews;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getTtlSeconds() {
		return ttlSeconds;
	}

	public void setTtlSeconds(Integer ttlSeconds) {
		this.ttlSeconds = ttlSeconds;
	}

	public Integer getMaxViews() {
		return maxViews;
	}

	public void setMaxViews(Integer maxViews) {
		this.maxViews = maxViews;
	}
}
