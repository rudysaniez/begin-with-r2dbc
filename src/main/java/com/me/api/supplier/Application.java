package com.me.api.supplier;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.me.api.supplier.config.PropertiesConfig.PaginationInformation;

@EnableConfigurationProperties(PaginationInformation.class)
@EnableR2dbcRepositories
@EnableScheduling
@EnableTransactionManagement
@ComponentScan(basePackages = "com.me.api.supplier")
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		
		SpringApplication app = new SpringApplication(Application.class);
		app.setWebApplicationType(WebApplicationType.REACTIVE);
		app.setBannerMode(Mode.CONSOLE);
		app.run(args);
	}
	
	@Bean
	public EmbeddedDatabase datasource() {
		
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).
				setName("suppliersdb").
				build();
	}
}
