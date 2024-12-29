package com.sparksInTheStep.webBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WebBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebBoardApplication.class, args);
	}

}
