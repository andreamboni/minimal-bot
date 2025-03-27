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

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:135.0) Gecko/20100101 Firefox/135.0";

    public List<BlogPost> getBlogPosts(String url) {
        try {
            LOGGER.info("Starting web scraping");
            Document document = Jsoup
                    .connect(url)
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

}
