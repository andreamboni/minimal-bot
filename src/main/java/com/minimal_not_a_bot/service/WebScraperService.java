package com.minimal_not_a_bot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.minimal_not_a_bot.model.BlogPost;

@Service
public class WebScraperService {
    private static final Logger LOGGER = LogManager.getLogger(WebScraperService.class);

    private static final String URL = "https://georgerrmartin.com/notablog/";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:135.0) Gecko/20100101 Firefox/135.0";

    public List<BlogPost> getBlogPosts() {
        try {
            LOGGER.info("Stating web scraping");
            Document document = Jsoup
                    .connect(URL)
                    .userAgent(USER_AGENT)
                    .header("Accept-Language", "*")
                    .get();

            return document.body()
                    .getElementsByClass("post-main")
                    .stream()
                    .map(BlogPostService::blogPostBuilder)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            LOGGER.info("Error while web scraping {}", e.getMessage());
            e.printStackTrace();
        }

        LOGGER.info("Finishing up web scraping");
        return List.of();
    }

    // private String buildContent(List<String> contentList) {
    // StringBuilder content = new StringBuilder();
    // for(String c : contentList) {
    // content.append(c);
    // content.append(System.lineSeparator());
    // }

    // return content.toString();
    // }

    // private String buildMentions(List<String> mentionsOfNextBook) {
    // StringBuilder mentions = new StringBuilder();
    // int mentionNumber = 1;
    // for(String m : mentionsOfNextBook) {
    // mentions.append("Mention number " + mentionNumber);
    // mentions.append(m);
    // mentions.append(System.lineSeparator());
    // }
    // return mentions.toString();
    // }

}
