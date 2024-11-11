package com.minimal_not_a_bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.minimal_not_a_bot.bot.Bot;
import com.minimal_not_a_bot.service.MessageService;

@SpringBootApplication
public class MinimalNotABotApplication {

	@Autowired
	private MessageService messageService;

	@Autowired
	private Bot bot;

	public static void main(String[] args) {
		SpringApplication.run(MinimalNotABotApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	private void sendMessage() {
		TelegramBotsApi botsApi;
		try {
			botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(bot);

			bot.sendText(5186470566L, "Mensagem teste", "HTML");

		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// messageService.sendMessage();
	}

}
