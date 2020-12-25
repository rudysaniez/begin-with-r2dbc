package com.me.r2dbc.h2.review.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.me.r2dbc.h2.review.bo.Review;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Profile("dev")
@Service
public class ScheduleReviewService {

	@Autowired private JdbcTemplate jdbcTemplate;
	@Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private static Random random = new Random();

	@Scheduled(fixedRate = 3000)
	public void schedule() {
		
		Integer reviewID = random.nextInt(10000);
		Integer productID = random.nextInt(100);
		
		Map<String, Object> namedParameters = new HashMap<>();
		namedParameters.put("reviewID", reviewID);
		namedParameters.put("productID", productID);
		namedParameters.put("author", "rsaniez");
		namedParameters.put("subject", "rsaniez");
		namedParameters.put("content", "rsaniez");
		namedParameters.put("creationDate", Timestamp.valueOf(LocalDateTime.now()));
		
		namedParameterJdbcTemplate.update(Review.insert, namedParameters);
		
		log.info(" > A review has been created (scheduled) : " + reviewID.toString());
	}
	
	@Scheduled(fixedRate = 5000)
	public void count() {
		
		log.info(" > Count = " + jdbcTemplate.queryForObject(Review.count, Long.class));
	}
}
