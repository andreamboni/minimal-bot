package com.minimal_not_a_bot.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimal_not_a_bot.model.BlogPost;
import com.minimal_not_a_bot.repository.BlogPostRepository;

@Service
public class WebScraperService {
    private static final Logger LOGGER = LogManager.getLogger(WebScraperService.class);
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:135.0) Gecko/20100101 Firefox/135.0";

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private MessageService messageService;

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

    public void scrapeNotABlog(String url, boolean sendMessage) {
        LOGGER.info("Starting Not A Blog Scraping");

        List<BlogPost> blogPosts = getBlogPosts(url);
        LOGGER.info("Blog Posts fetched {}", blogPosts.size());

        for (BlogPost blogPost : blogPosts) {
            Optional<BlogPost> blogPostHashCode = blogPostRepository.findByPostHashCode(blogPost.getPostHashCode());
            if (!blogPostHashCode.isPresent()) {
                LOGGER.info("Saving blog post \"{}\"", blogPost.getTitle());
                blogPostRepository.save(blogPost);
                if (sendMessage) {
                    messageService.sendMessage(blogPost);
                }
            } else {
                LOGGER.info("Blog post \"{}\" already exists", blogPost.getTitle());
            }
        }

        LOGGER.info("Finishing Not A Blog Scraping");
    }

}
