package com.minimal_not_a_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MinimalNotABotApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MinimalNotABotApplication.class, args);
		SpringApplication.exit(context, () -> 0);
	}

}
