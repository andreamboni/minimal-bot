package com.minimal_not_a_bot.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimal_not_a_bot.model.BlogPost;
import com.minimal_not_a_bot.repository.BlogPostRepository;

@Service
public class BlogPostService {
    private static final Logger LOGGER = LogManager.getLogger(BlogPostService.class);

    @Autowired
    private BlogPostRepository repository;

    public boolean postExist(String postHashCode, BlogPost blogPost) {
        Optional<BlogPost> hashCode = repository.findByPostHashCode(postHashCode);

        if (hashCode.isPresent()) {
            LOGGER.info("Post \"{}\" already exist.", blogPost.getTitle());
            return true;
        }

        repository.save(blogPost);
        return false;
    }
}
