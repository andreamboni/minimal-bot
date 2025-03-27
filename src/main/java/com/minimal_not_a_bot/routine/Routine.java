package com.minimal_not_a_bot.routine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.minimal_not_a_bot.service.ArchiveService;

import jakarta.annotation.PostConstruct;

@Component
public class Routine {

    private static final Logger LOGGER = LogManager.getLogger(Routine.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ArchiveService archiveService;

    @PostConstruct
    public void mainRoutine() {
        LOGGER.info("Starting routine.");
        try {
            archiveService.archiveBlogPosts();
        } catch (Exception e) {
            LOGGER.error("Error while running routine: {}", e);
            e.printStackTrace();
        }
        LOGGER.info("Finishing up routine.");
        SpringApplication.exit(context, () -> 0);
    }

}
