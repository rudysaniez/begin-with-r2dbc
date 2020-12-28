package com.me.r2dbc.h2.review.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Review {

	@JsonProperty("reviewID")
	private Integer reviewID;
	
	@JsonProperty("productID")
	private Integer productID;
	
	@JsonProperty("author")
	private String author;
	
	@JsonProperty("subject")
	private String subject;
	
	@JsonProperty("content")
	private String content;
	
	@JsonProperty("creationDate")
	private LocalDateTime creationDate;
	
	@JsonProperty("updateDate")
	private LocalDateTime updateDate;
}
