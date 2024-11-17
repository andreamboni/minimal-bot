package com.minimal_not_a_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MinimalNotABotApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinimalNotABotApplication.class, args);
	}

}
