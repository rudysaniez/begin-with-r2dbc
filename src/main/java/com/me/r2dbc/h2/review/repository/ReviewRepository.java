package com.me.r2dbc.h2.review.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.me.r2dbc.h2.review.bo.Review;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional
public interface ReviewRepository extends ReactiveCrudRepository<Review, Integer> {

	/**
	 * @param reviewID
	 * @return mono of {@link Review}
	 */
	@Transactional(readOnly = true)
	public Mono<Review> findByReviewID(Integer reviewID);
	
	/**
	 * @param productID
	 * @param page
	 * @return flux of {@link Review}
	 */
	@Transactional(readOnly = true)
	public Flux<Review> findByProductID(Integer productID);
}
