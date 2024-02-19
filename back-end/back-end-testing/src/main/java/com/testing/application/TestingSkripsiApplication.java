package com.testing.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "com.testing")
@EnableMongoRepositories(basePackages = "com.testing.repository")
public class TestingSkripsiApplication {

	// to open swagger hit link http://localhost:8080/swagger-ui/index.html
	public static void main(String[] args) {
		SpringApplication.run(TestingSkripsiApplication.class, args);
	}

}
