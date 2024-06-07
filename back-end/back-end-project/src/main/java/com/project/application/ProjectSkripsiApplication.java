package com.project.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "com.project")
@EnableMongoRepositories(basePackages = "com.project.repository")
public class ProjectSkripsiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectSkripsiApplication.class, args);
	}

}
