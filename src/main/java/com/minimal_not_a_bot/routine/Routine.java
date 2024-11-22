package com.minimal_not_a_bot.routine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.minimal_not_a_bot.service.ArchiveService;
import com.minimal_not_a_bot.service.MessageService;

import jakarta.annotation.PostConstruct;

@Component
public class Routine {

    private static final Logger LOGGER = LogManager.getLogger(Routine.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private ArchiveService archiveService;

    @PostConstruct
    public void mainRoutine() {
        LOGGER.info("Starting routine.");
        try {
            messageService.sendMessage();
            archiveService.archivePosts();
        } catch (Exception e) {
            LOGGER.error("Error while running routine: {}", e);
        }
        LOGGER.info("Finishing up routine.");
        System.exit(0);
    }

}
