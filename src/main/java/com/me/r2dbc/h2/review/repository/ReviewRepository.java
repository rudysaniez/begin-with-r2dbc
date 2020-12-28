package com.me.r2dbc.h2.review.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.me.r2dbc.h2.review.bo.ReviewEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional
public interface ReviewRepository extends ReactiveCrudRepository<ReviewEntity, Integer> {

	/**
	 * @param reviewID
	 * @return mono of {@link ReviewEntity}
	 */
	@Transactional(readOnly = true)
	public Mono<ReviewEntity> findByReviewID(Integer reviewID);
	
	/**
	 * @param productID
	 * @param page
	 * @return flux of {@link ReviewEntity}
	 */
	@Transactional(readOnly = true)
	public Flux<ReviewEntity> findByProductID(Integer productID, Pageable page);
	
	/**
	 * @param productID
	 * @return number of {@link Review} by productID
	 */
	@Transactional(readOnly = true)
	public Mono<Long> countByProductID(Integer productID);
	
	/**
	 * @param page
	 * @return flux of {@link ReviewEntity}
	 */
	@Transactional(readOnly = true)
	public Flux<ReviewEntity> findByProductIDNotNull(Pageable page);

	/**
	 * @param productID
	 * @return mono of {@link Void}
	 */
	public Mono<Void> deleteByProductID(Integer productID);
}
