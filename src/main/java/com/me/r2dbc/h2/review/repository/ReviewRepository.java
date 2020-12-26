package com.me.r2dbc.h2.review.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.me.r2dbc.h2.review.bo.Review;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReviewRepository extends ReactiveCrudRepository<Review, Integer> {

	/**
	 * @param reviewID
	 * @return mono of {@link Review}
	 */
	public Mono<Review> findByReviewID(Integer reviewID);
	
	/**
	 * @param productID
	 * @return flux of {@link Review}
	 */
	public Flux<Review> findByProductID(Integer productID);
}
