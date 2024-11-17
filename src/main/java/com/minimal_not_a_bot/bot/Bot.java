package com.minimal_not_a_bot.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {

    private static final String TELEGRAM_TOKEN = System.getenv("TELEGRAM_TOKEN");

    public void sendText(Long who, String what, String parse_mode) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .parseMode(parse_mode)
                .text(what).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        User user = msg.getFrom();


        System.out.println(user.getFirstName() + " wrote " + msg.getText());
    }

    @Override
    public String getBotToken() {
        return TELEGRAM_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return "not_a_grrm_updates_bot";
    }

}
