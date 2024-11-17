package com.minimal_not_a_bot.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimal_not_a_bot.model.MinimalPost;
import com.minimal_not_a_bot.repository.MinimalBotRepository;

@Service
public class MinimalBotService {
    private static final Logger LOGGER = LogManager.getLogger(MinimalBotService.class);

    @Autowired
    private MinimalBotRepository repository;

    public boolean postExist(String postHashCode, String postTitle) {
        Optional<MinimalPost> hashCode = repository.findByPostHashCode(postHashCode);

        if(hashCode.isPresent()) {
            LOGGER.info("Post {} já existe.", postTitle);
            return true;
        }

        repository.saveAndFlush(MinimalPost
                .builder()
                .postHashCode(postHashCode)
                .insertTs(LocalDateTime.now())
                .build());

        return false;
    }

}
