package com.minimal_not_a_bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.minimal_not_a_bot.service.MessageService;

@SpringBootApplication
public class MinimalNotABotApplication {

	@Autowired
	private MessageService messageService;

	public static void main(String[] args) {
		SpringApplication.run(MinimalNotABotApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	private void sendMessage() {
		messageService.sendMessage();
	}

}
