package com.minimal_not_a_bot.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArchiveService {
    private static final Logger LOGGER = LogManager.getLogger(ArchiveService.class);
    private static final String URL = "https://georgerrmartin.com/notablog/";

    @Autowired
    private WebScraperService webScraperService;

    public void archiveBlogPosts() {
        LOGGER.info("Starting archiving posts process.");

        webScraperService.scrapeNotABlog(URL, true);

        LOGGER.info("Finishing up archiving posts process.");
    }

    public void archiveWholeNotABlog() {
        LOGGER.info("Starting archiving posts process.");

        for (int i = 0; i < 300; i++) {
            String url = "https://georgerrmartin.com/notablog/";

            if (i > 0) {
                int pageNumber = i + 1;
                url = url + "page/" + pageNumber;
            }

            LOGGER.info("Scrapping page {}", url);

            webScraperService.scrapeNotABlog(url, false);
        }

        LOGGER.info("Finishing up archiving posts process.");
    }
}
