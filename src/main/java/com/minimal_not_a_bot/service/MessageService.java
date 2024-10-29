package com.minimal_not_a_bot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.minimal_not_a_bot.bot.Bot;
import com.minimal_not_a_bot.model.BlogPost;
import com.minimal_not_a_bot.util.HashUtil;

@Service
public class MessageService {

    @Autowired
    private WebScraperService scraper;

    @Autowired
    private MinimalBotService service;

    @Autowired
    private Bot bot;

    public void sendMessage() {
        TelegramBotsApi botsApi;
        try {
            botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);

            List<BlogPost> blogPosts = scraper.getBlogPosts();

            if (!blogPosts.isEmpty()) {
                BlogPost blogPost = blogPosts.get(0);
                String postTitle = blogPost.getTitle();
                String theDate = blogPost.getTheDate();
                String url = blogPost.getUrl();
                List<String> mentionsOfNextBook = blogPost.getMentionsOfNextBook();

                String hash = HashUtil.generateHash(postTitle, theDate, url);

                if (!service.postExist(hash)) {
                    String message = buildMessage(postTitle, url, mentionsOfNextBook);

                    bot.sendText(5186470566L, message, "HTML");
                } else {
                    bot.sendText(5186470566L, "Sem post novo por enquanto", "HTML");
                }

            }

        } catch (TelegramApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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
