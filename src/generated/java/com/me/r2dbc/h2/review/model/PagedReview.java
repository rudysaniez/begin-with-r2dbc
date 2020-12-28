package com.me.r2dbc.h2.review.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PagedReview {

	@JsonProperty("content")
	@Valid @Builder.Default
	private List<Review> content = new ArrayList<>();

	@JsonProperty("page")
	private PageMetadata page;
	
	public PagedReview addContent(Review review) {
		
		content.add(review);
		return this;
	}
}
