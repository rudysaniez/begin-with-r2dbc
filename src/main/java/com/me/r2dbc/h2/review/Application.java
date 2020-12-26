package com.me.r2dbc.h2.review;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.me.r2dbc.h2.review.bo.Review;
import com.me.r2dbc.h2.review.service.AsciiArtService;

@EnableR2dbcRepositories
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
public class Application {

	@Bean
	public EmbeddedDatabase datasource() {
		
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).
				setName("reviewsdb").
				build();
	}
	
	public static void main(String[] args) {
		
		SpringApplication app = new SpringApplication(Application.class);
		app.setWebApplicationType(WebApplicationType.REACTIVE);
		app.run(args);
	}

	@Profile("dev-init")
	@Bean @Autowired
	public ApplicationRunner setup(NamedParameterJdbcTemplate jdbcTemplate, AsciiArtService asciiService) {
		
		return args -> {
			
			asciiService.display("REVIEW SETUP");
			
			Map<String, Object> namedParameters = new HashMap<>();
			namedParameters.put("reviewID", 999);
			namedParameters.put("productID", 999);
			namedParameters.put("author", "rsaniez");
			namedParameters.put("subject", "rsaniez");
			namedParameters.put("content", "rsaniez");
			namedParameters.put("creationDate", Timestamp.valueOf(LocalDateTime.now()));
			
			jdbcTemplate.update(Review.insert, namedParameters);
		};
	}
}
