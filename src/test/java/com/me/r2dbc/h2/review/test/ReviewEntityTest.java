package com.me.r2dbc.h2.review.test;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.me.r2dbc.h2.review.bo.Review;
import com.me.r2dbc.h2.review.exception.NotFoundException;
import com.me.r2dbc.h2.review.repository.ReviewRepository;
import com.me.r2dbc.h2.review.service.AsciiArtService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Transactional @Commit
@SpringBootTest
@RunWith(SpringRunner.class)
public class ReviewEntityTest {

	@Autowired private AsciiArtService asciiService;
	@Autowired private ReviewRepository reviewRepository;
	
	Review reviewSaved;
	
	@Before
	public void setup() {
		
		asciiService.display("SETUP");
		
		reviewRepository.deleteAll().block();
		
		reviewSaved = reviewRepository.save(new Review(null, 900, 900, "rsaniez", "rsaniez", "rsaniez")).block();
		
		int size = reviewRepository.saveAll(
			IntStream.rangeClosed(100, 120).mapToObj(reviewID -> new Review(null, reviewID, 1, "rsaniez", "subject_" + reviewID, 
					"content_" + reviewID)).collect(Collectors.toList())
		).collectList().block().size();
		
		assertTrue(size > 0);
	}
	
	@Test
	public void findByReviewID() {
		
		StepVerifier.create(reviewRepository.findByReviewID(900)).
			expectNextMatches(r -> r.getAuthor().equals("rsaniez") && r.getReviewID().equals(900)).verifyComplete();
	}
	
	@Test(expected = NotFoundException.class)
	public void findNotFoundException() {
		
		reviewRepository.findByReviewID(999).
			switchIfEmpty(Mono.error(new NotFoundException())).block();
	}
	
	@Test
	public void findAll() {
		
		List<Review> reviews = reviewRepository.findAll().filter(r -> r.getReviewID() > 100 && r.getReviewID() < 300).
				collectList().block();
		
		assertTrue(reviews.size() > 0);
	}
}
