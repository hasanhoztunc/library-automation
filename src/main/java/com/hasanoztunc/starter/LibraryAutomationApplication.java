package com.hasanoztunc.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = { "com.hasanoztunc" })
@EntityScan(basePackages = { "com.hasanoztunc" })
@EnableJpaRepositories(basePackages = { "com.hasanoztunc" })
@SpringBootApplication
public class LibraryAutomationApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryAutomationApplication.class, args);
	}

}
