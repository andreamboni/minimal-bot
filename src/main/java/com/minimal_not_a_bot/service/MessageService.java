package com.minimal_not_a_bot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.minimal_not_a_bot.bot.Bot;
import com.minimal_not_a_bot.model.BlogPost;
import com.minimal_not_a_bot.util.HashUtil;

@Service
public class MessageService {
    private static final Logger LOGGER = LogManager.getLogger(MessageService.class);

    @Autowired
    private WebScraperService scraper;

    @Autowired
    private BlogPostService service;

    @Autowired
    private Bot bot;

    private static final String TELEGRAM_USER = System.getenv("TELEGRAM_USER");

    public void sendMessage() {
        LOGGER.info("Starting sending message process.");
        TelegramBotsApi botsApi;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            BotSession registerBot = botsApi.registerBot(bot);

            BlogPost blogPost = scraper.getLastBlogPost();

            if (blogPost != null) {
                String postTitle = blogPost.getTitle();
                String theDate = blogPost.getTheDate();
                String url = blogPost.getUrl();
                List<String> mentionsOfNextBook = blogPost.getMentionsOfNextBook();

                String hash = HashUtil.generateHash(postTitle, theDate, url);

                if (!service.postExist(hash, blogPost)) {
                    String message = buildMessage(postTitle, url, mentionsOfNextBook);

                    bot.sendText(Long.parseLong(TELEGRAM_USER), message, "HTML");
                    if(registerBot.isRunning()) {
                        LOGGER.info("Stopping bot");
                        registerBot.stop();
                    }
                }
            }

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        LOGGER.info("Finishing up sending message process.");
    }

    private String buildMessage(String postTitle, String url, List<String> mentionsOfNextBook) {
        StringBuilder sb = new StringBuilder()
                .append("⚔️ <b>Novo post do George</b> ⚔️\n\n")
                .append("<b>Título do post: </b>")
                .append(String.format("<a href=\"%s\">%s</a>\n", url, postTitle));

        if (mentionsOfNextBook.isEmpty()) {
            sb.append("The Winds of Winter não foi mencionado. 😭");
        } else {
            int i = 1;
            for (String nextBook : mentionsOfNextBook) {
                sb.append(String.format("<b>Menção # %d:</b>\n<blockquote>%s</blockquote>\n", i++, nextBook.trim()));
            }
        }

        return sb.toString();
    }

}
