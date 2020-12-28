package com.me.r2dbc.h2.review.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.me.r2dbc.h2.review.bo.ReviewEntity;
import com.me.r2dbc.h2.review.exception.InvalidInputException;
import com.me.r2dbc.h2.review.exception.NotFoundException;
import com.me.r2dbc.h2.review.repository.ReviewRepository;

import io.r2dbc.spi.R2dbcDataIntegrityViolationException;
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
		Integer productID = random.nextInt(20);
		
		ReviewEntity toSaved = new ReviewEntity(null, reviewID, productID, "rsaniez", "Subject : " + reviewID, "Review : " + reviewID);
		
		Mono<ReviewEntity> created = reviewRepository.save(toSaved).
				onErrorMap(R2dbcDataIntegrityViolationException.class, e -> new InvalidInputException(String.format("The review with reviewID=%d already exists.", reviewID))).
				log();
		
		log.info(" > A review has been created (scheduled with r2dbc repository) : " + created.block().toString());
	}
	
	@Scheduled(fixedRate = 10000)
	public void count() {
		
		log.info(" > Count = " + jdbcTemplate.getJdbcTemplate().queryForObject(ReviewEntity.count, Long.class));
	}
	
	@Scheduled(fixedRate = 30000)
	public void scheduleFindReactive() {
		
		Integer productID = random.nextInt(20);
		
		log.info(" > {} review(s) found with the productID = {}",
			
			reviewRepository.findByProductID(productID, PageRequest.of(0, 20)).
				switchIfEmpty(Mono.error(new NotFoundException(String.format("Reviews with productID=%d doesn't not exists.", productID)))).
				collectList().map(l -> l.size()).block(),
				
			productID);
		
		
	}
}
