package com.me.r2dbc.h2.review.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @Builder @AllArgsConstructor
public class PageMetadata {

	@JsonProperty("size")
	private Long size;

	@JsonProperty("totalElements")
	private Long totalElements;

	@JsonProperty("totalPages")
	private Long totalPages;

	@JsonProperty("number")
	private Long number;
}
