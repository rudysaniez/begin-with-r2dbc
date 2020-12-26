package com.me.r2dbc.h2.review.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.me.r2dbc.h2.review.bo.Review;
import com.me.r2dbc.h2.review.exception.NotFoundException;
import com.me.r2dbc.h2.review.repository.ReviewRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Profile("dev-scheduler")
@Service
public class ScheduleReviewService {

	@Autowired private NamedParameterJdbcTemplate jdbcTemplate;
	@Autowired private ReviewRepository reviewRepository;
	
	private static Random random = new Random();

	@Scheduled(fixedRate = 5000)
	public void scheduleReactive() {
		
		Integer reviewID = random.nextInt(10000);
		Integer productID = random.nextInt(100);
		
		Review toSaved = new Review(null, reviewID, productID, "rsaniez", "Subject : " + reviewID, "Review : " + reviewID);
		
		Mono<Review> created = reviewRepository.save(toSaved);
		
		log.info(" > A review has been created (scheduled with r2dbc repository) : " + created.block().toString());
	}
	
	@Scheduled(fixedRate = 10000)
	public void count() {
		
		log.info(" > Count = " + jdbcTemplate.getJdbcTemplate().queryForObject(Review.count, Long.class));
	}
	
	@Scheduled(fixedRate = 30000)
	public void scheduleFindReactive() {
		
		Integer productID = random.nextInt(100);
		
		log.info(" > {} review(s) found with the productID = {}",
			
				reviewRepository.findByProductID(productID).
				switchIfEmpty(Mono.error(new NotFoundException(String.format("Reviews with productID=%d doesn't not exists.", productID)))).
				collectList().map(l -> l.size()).block(),
				
			productID);
		
		
	}
}
