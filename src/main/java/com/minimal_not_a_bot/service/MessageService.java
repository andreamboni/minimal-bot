package com.minimal_not_a_bot.service;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.minimal_not_a_bot.bot.BotService;
import com.minimal_not_a_bot.model.BlogPost;

@Service
public class MessageService {
    private static final Logger LOGGER = LogManager.getLogger(MessageService.class);

    @Autowired
    private BotService botService;

    private static final String TELEGRAM_USER = System.getenv("TELEGRAM_USER");

    public void sendMessage(BlogPost blogPost) {
        LOGGER.info("Starting sending message process.");
        TelegramBotsApi botsApi;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            BotSession registerBot = botsApi.registerBot(botService);

            Map<String, List<String>> asoiafBooksMentions = blogPost.getASOIAFBooksMentions();
            List<String> theWindsOfWinterMentions = asoiafBooksMentions.get("THE WINDS OF WINTER");

            String message = buildMessage(blogPost.getTitle(), blogPost.getUrl(), theWindsOfWinterMentions);

            botService.sendText(Long.parseLong(TELEGRAM_USER), message, "HTML");
            if (registerBot.isRunning()) {
                LOGGER.info("Stopping bot");
                registerBot.stop();
            }

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        LOGGER.info("Finishing up sending message process.");
    }

    private String buildMessage(String postTitle, String url, List<String> theWindsOfWinterMentions) {
        StringBuilder sb = new StringBuilder()
                .append("⚔️ <b>Novo post do George</b> ⚔️\n\n")
                .append("<b>Título do post: </b>")
                .append(String.format("<a href=\"%s\">%s</a>\n", url, postTitle));

        if (theWindsOfWinterMentions == null) {
            sb.append("The Winds of Winter não foi mencionado. 😭");
        } else {
            int i = 1;
            for (String nextBook : theWindsOfWinterMentions) {
                sb.append(String.format("<b>Menção # %d:</b>\n<blockquote>%s</blockquote>\n", i++, nextBook.trim()));
            }
        }

        return sb.toString();
    }

}
