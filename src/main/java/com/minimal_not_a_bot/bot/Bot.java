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

    public void sendText(Long who, String what, String parse_mode) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) // Who are we sending a message to
                .parseMode(parse_mode)
                .text(what).build(); // Message content
        try {
            execute(sm); // Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e); // Any error will be printed here
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
        return "7132472651:AAHoJqz8FSH1zr1M-rXNtz6CawEFN0bcvM4";
    }

    @Override
    public String getBotUsername() {
        return "not_a_grrm_updates_bot";
    }

}
