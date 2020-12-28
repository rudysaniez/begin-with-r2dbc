package com.me.r2dbc.h2.review.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;
import org.mapstruct.factory.Mappers;

import com.me.r2dbc.h2.review.bo.ReviewEntity;
import com.me.r2dbc.h2.review.mapper.ReviewMapper;
import com.me.r2dbc.h2.review.model.Review;

public class ReviewMapperTest {

	private ReviewMapper reviewMapper = Mappers.getMapper(ReviewMapper.class);
	
	@Test
	public void reviewMapper() {
		
		Review review = Review.builder().author("rsaniez").content("Analyse ok.").creationDate(LocalDateTime.now()).
				productID(900).reviewID(900).subject("Analyse").build();
		
		ReviewEntity entity = reviewMapper.toEntity(review);
		
		assertReview(review, reviewMapper.toModel(entity));
		
	}
	
	/**
	 * @param actual
	 * @param expected
	 */
	private void assertReview(Review actual, Review expected) {
		
		assertEquals(actual.getContent(), expected.getContent());
		assertEquals(actual.getProductID(), expected.getProductID());
		assertEquals(actual.getReviewID(), expected.getReviewID());
		assertEquals(actual.getSubject(), expected.getSubject());
	}
}
