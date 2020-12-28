package com.me.r2dbc.h2.review.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.me.r2dbc.h2.review.bo.ReviewEntity;
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
	
	ReviewEntity reviewSaved;
	
	@Before
	public void setup() {
		
		asciiService.display("SETUP");
		
		reviewRepository.deleteAll().block();
		
		reviewSaved = reviewRepository.save(new ReviewEntity(null, 900, 900, "rsaniez", "rsaniez", "rsaniez")).block();
		
		int size = reviewRepository.saveAll(
			IntStream.rangeClosed(100, 120).mapToObj(reviewID -> new ReviewEntity(null, reviewID, 1, "rsaniez", "subject_" + reviewID, 
					"content_" + reviewID)).collect(Collectors.toList())
		).collectList().block().size();
		
		assertTrue(size > 0);
	}
	
	@Test
	public void findByReviewID() {
		
		asciiService.display("FIND BY REVIEW ID");
		
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
		
		asciiService.display("FIND ALL");
		
		List<ReviewEntity> reviews = reviewRepository.findAll().filter(r -> r.getReviewID() > 100 && r.getReviewID() < 300).
				collectList().block();
		
		assertTrue(reviews.size() > 0);
		
		reviews = reviewRepository.findByProductIDNotNull(PageRequest.of(0, 10, Sort.by(Direction.ASC, "reviewID"))).
				collectList().block();
		
		assertTrue(reviews.size() > 0);
	}
	
	@Test
	public void findByProductID() {
		
		asciiService.display("FIND BY PRODUCT ID");
		
		List<ReviewEntity> reviews = reviewRepository.
				findByProductID(1, PageRequest.of(0, 20, Sort.by(Direction.ASC, "reviewID"))).collectList().block();
		
		assertThat(reviews).isNotEmpty();
			
		Long count = reviewRepository.countByProductID(1).block();
		assertTrue(count > 0);
	}
}
