package com.minimal_not_a_bot.routine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.minimal_not_a_bot.service.ArchiveService;

import jakarta.annotation.PostConstruct;

@Component
public class Routine {

    private static final Logger LOGGER = LogManager.getLogger(Routine.class);

    @Autowired
    private ArchiveService archiveService;

    @PostConstruct
    public void mainRoutine() {
        LOGGER.info("Starting routine.");
        try {
            archiveService.archiveBlogPosts();
        } catch (Exception e) { // TODO: deveria ser Exception mesmo? não pode ser algo mais especifico? 
            LOGGER.error("Error while running routine: {}", e);
            e.printStackTrace();
        }
        LOGGER.info("Finishing up routine.");
        // System.exit(0);
    }

}
