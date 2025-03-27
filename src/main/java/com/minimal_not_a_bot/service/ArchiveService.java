package com.minimal_not_a_bot.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimal_not_a_bot.model.BlogPost;
import com.minimal_not_a_bot.repository.BlogPostRepository;

@Service
public class ArchiveService {
    private static final Logger LOGGER = LogManager.getLogger(ArchiveService.class);

    private static final String URL = "https://georgerrmartin.com/notablog/";
    private static final String LAST_PAGE_URL = "https://georgerrmartin.com/notablog/page/270";

    @Autowired
    private WebScraperService webScraperService;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private MessageService messageService;

    public void archiveBlogPosts() {
        LOGGER.info("Starting archiving posts process.");

        List<BlogPost> blogPosts = webScraperService.getBlogPosts(URL);
        LOGGER.info("Blog Posts fetched {}", blogPosts.size());

        for(BlogPost blogPost : blogPosts) {
            Optional<BlogPost> blogPostHashCode = blogPostRepository.findByPostHashCode(blogPost.getPostHashCode());
            if(!blogPostHashCode.isPresent()) {
                LOGGER.info("Saving blog post \"{}\"", blogPost.getTitle());
                blogPostRepository.save(blogPost);
                messageService.sendMessage(blogPost);
            } else {
                LOGGER.info("Blog post \"{}\" already exists", blogPost.getTitle());
            }
        }

        LOGGER.info("Finishing up archiving posts process.");
    }

    public void archiveWholeNotABlog() {
        LOGGER.info("Starting archiving posts process.");

        for(int i = 0; i < 300; i++) {
            String url = "https://georgerrmartin.com/notablog/";

            if(i > 0) {
                int pageNumber = i + 1;
                url = url + "page/" + pageNumber;
            }

            LOGGER.info("Scrapping page {}", url);

            List<BlogPost> blogPosts = webScraperService.getBlogPosts(url);
            LOGGER.info("Blog Posts fetched {}", blogPosts.size());
    
            for(BlogPost blogPost : blogPosts) {
                Optional<BlogPost> blogPostHashCode = blogPostRepository.findByPostHashCode(blogPost.getPostHashCode());
                if(!blogPostHashCode.isPresent()) {
                    LOGGER.info("Saving blog post \"{}\"", blogPost.getTitle());
                    blogPostRepository.save(blogPost);
                    messageService.sendMessage(blogPost);
                } else {
                    LOGGER.info("Blog post \"{}\" already exists", blogPost.getTitle());
                }
            }
        }


        LOGGER.info("Finishing up archiving posts process.");
    }
}
