package com.minimal_not_a_bot.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimal_not_a_bot.model.BlogPages;
import com.minimal_not_a_bot.model.BlogPost;
import com.minimal_not_a_bot.repository.BlogPagesRepository;
import com.minimal_not_a_bot.repository.BlogPostRepository;

@Service
public class ArchiveService {
    private static final Logger LOGGER = LogManager.getLogger(ArchiveService.class);

    private static final String BASE_URL = "https://georgerrmartin.com/notablog/page/";

    private static final String LAST_PAGE = "265";

    @Autowired
    private WebScraperService webScraperService;

    @Autowired
    private BlogPagesRepository blogPagesRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    public void archivePosts() {
        LOGGER.info("Starting archiving posts process.");
        List<BlogPages> blogPages = blogPagesRepository.findAll();
        String pageNumber = null;
        StringBuilder url = new StringBuilder();

        if (blogPages.isEmpty()) {
            pageNumber = LAST_PAGE;
        } else {
            pageNumber = blogPages.get(0).getPageNumber();
        }

        if (Integer.parseInt(pageNumber) >= 2) {
            url.append(BASE_URL);
            url.append(pageNumber);

            LOGGER.info("Searching for the URL: {}", url.toString());

            List<BlogPost> blogPosts = webScraperService.getBlogPosts(url.toString());

            for(BlogPost blogPost : blogPosts) {
                LOGGER.info("Saving blog post \"{}\"", blogPost.getTitle());
                blogPostRepository.save(blogPost);
            }
            
            // List<String> titles = blogPosts.stream().map(BlogPost::getTitle).toList();
            // LOGGER.info("Saving blog posts for the page {}, with titles {}", pageNumber, titles.toString());
            // blogPostRepository.saveAll(blogPosts);
            
            LOGGER.info("Cleaning blog-pages collection");
            blogPagesRepository.deleteAll();
            
            Integer newPageNumber = Integer.parseInt(pageNumber) - 1;
            LOGGER.info("Saving page number {} into blog-pages collection", newPageNumber);
            blogPagesRepository.save(BlogPages.builder().pageNumber(newPageNumber.toString()).build());

            LOGGER.info("Finishing up archiving posts process.");
        }

    }

}
