package com.me.r2dbc.h2.review.test;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.me.r2dbc.h2.review.bo.Review;
import com.me.r2dbc.h2.review.service.AsciiArtService;

@DataJdbcTest
@Transactional @Commit
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@RunWith(SpringRunner.class)
public class ReviewEntityTest {

	@Autowired private AsciiArtService asciiService;
	@Autowired private NamedParameterJdbcTemplate jdbcTemplate;
	
	Review reviewSaved;
	
	@Before
	public void setup() {
		
		asciiService.display("SETUP");
		
		jdbcTemplate.getJdbcTemplate().execute(Review.deleteAll);
		
		Map<String, Object> namedParameters = new HashMap<>();
		namedParameters.put("reviewID", 900);
		namedParameters.put("productID", 900);
		namedParameters.put("author", "rsaniez");
		namedParameters.put("subject", "rsaniez");
		namedParameters.put("content", "rsaniez");
		namedParameters.put("creationDate", Timestamp.valueOf(LocalDateTime.now()));
		
		int result = jdbcTemplate.update(Review.insert, namedParameters);
		
		assertTrue(result > 0);
	}
	
	@Test
	public void findByReviewID() {
		
		Map<String, Object> namedParameters = new HashMap<>();
		namedParameters.put("reviewID", 900);
		
		List<Review> reviews = jdbcTemplate.query(Review.findByReviewID, namedParameters, (rs, i) -> {
			
			Integer id = rs.getInt("id");
			Integer reviewID = rs.getInt("reviewID");
			Integer productID = rs.getInt("productID");
			String author = rs.getString("author");
			String subject = rs.getString("subject");
			String content = rs.getString("content");
			
			return Review.builder().author(author).content(content).
					productID(productID).reviewID(reviewID).subject(subject).id(id).build();
		});
		
		assertTrue(!reviews.isEmpty());
	}
}
