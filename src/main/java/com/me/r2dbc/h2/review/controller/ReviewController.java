package com.me.r2dbc.h2.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.me.r2dbc.h2.review.Api;
import com.me.r2dbc.h2.review.bo.Review;
import com.me.r2dbc.h2.review.exception.NotFoundException;
import com.me.r2dbc.h2.review.repository.ReviewRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class ReviewController {

	private final ReviewRepository reviewRepository;
	
	@Autowired
	public ReviewController(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	/**
	 * @param id
	 * @return {@link Review}
	 */
	@GetMapping(value = Api.REVIEW_PATH + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<Review>> getByReviewID(@PathVariable(required = true, name="id") Integer id) {
		
		log.info(" > Search a review by reviewID=%d", id);
		
		return reviewRepository.findByReviewID(id).
				switchIfEmpty(Mono.error(new NotFoundException(String.format("Reviews with productID=%d doesn't not exists.", id)))).
				map(r -> ResponseEntity.ok(r));
	}
}
