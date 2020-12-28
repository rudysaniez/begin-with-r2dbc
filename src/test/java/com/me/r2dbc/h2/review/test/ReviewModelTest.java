package com.me.r2dbc.h2.review.test;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import com.me.r2dbc.h2.review.model.PageMetadata;
import com.me.r2dbc.h2.review.model.PagedReview;
import com.me.r2dbc.h2.review.model.Review;

public class ReviewModelTest {

	@Test
	public void reviewModel() {
		
		Review review = Review.builder().author("rudysaniez").content("Review is ok.").
				creationDate(LocalDateTime.now()).productID(900).reviewID(900).subject("Analyse").build();
		
		Review review1 = Review.builder().author("rudysaniez").content("Review is already ok.").
				creationDate(LocalDateTime.now()).productID(900).reviewID(901).subject("Analyse").build();
		
		PageMetadata page = PageMetadata.builder().number(0L).size(10L).totalElements(2L).totalPages(1L).build();
		
		PagedReview pagedReview = new PagedReview();
		pagedReview.addContent(review).addContent(review1);
		pagedReview.setPage(page);
		
		assertTrue(pagedReview.getContent().size() == 2);
		assertTrue(pagedReview.getPage().getTotalElements() == 2L);
	}
}
