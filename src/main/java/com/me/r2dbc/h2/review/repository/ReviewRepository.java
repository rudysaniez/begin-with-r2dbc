package com.me.r2dbc.h2.review.repository;

import org.springframework.data.repository.CrudRepository;

import com.me.r2dbc.h2.review.bo.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer> {

	public Review findByReviewID(Integer id);
}
