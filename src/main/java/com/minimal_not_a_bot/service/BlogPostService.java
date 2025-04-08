package com.minimal_not_a_bot.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minimal_not_a_bot.model.BlogPost;
import com.minimal_not_a_bot.repository.BlogPostRepository;
import com.minimal_not_a_bot.util.HashUtil;

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

    public static BlogPost blogPostBuilder(Element blogPostHTML) {
        Elements titleElement = blogPostHTML.select("h1");

        String blogPostTitle = titleElement.text();
        String blogPostURL = titleElement.select("a").attr("href");

        String blogPostTheDate = getBlogPostTheDate(blogPostHTML);
        LocalDateTime blogPostTheDateFormatted = blogPostTheDateFormatter(blogPostTheDate).get();

        String blogPostProfileImage = blogPostHTML.select("img").attr("src");
        String blogPostTags = blogPostHTML.getElementsByClass("tagi").text();

        Elements blogPostElements = blogPostHTML.getElementsByClass("post");

        List<String> blogPostContent = getBlogPostContent(blogPostElements);
        Map<String, List<String>> ASOIAFBooksMentions = getASOIAFBooksMentions(blogPostElements);

        String blogPostHashCode = HashUtil.generateHash(blogPostTitle, blogPostTheDate, blogPostURL);

        return BlogPost.builder()
                .title(blogPostTitle)
                .url(blogPostURL)
                .theDate(blogPostTheDate)
                .theDateFormatted(blogPostTheDateFormatted)
                .profileImage(blogPostProfileImage)
                .content(blogPostContent)
                .tags(blogPostTags)
                .ASOIAFBooksMentions(ASOIAFBooksMentions)
                .postHashCode(blogPostHashCode)
                .insertTs(LocalDateTime.now())
                .build();
    }

    private static String getBlogPostTheDate(Element blogPostHTML) {
        String theDateWithoutHour = blogPostHTML.getElementsByClass("the-date").text();
        String theDateWithHour = blogPostHTML.getElementsByClass("thedate").text();

        if(!theDateWithoutHour.isEmpty()) {
            return theDateWithoutHour;
        } else {
            return theDateWithHour;
        }
    }

    private static List<String> getBlogPostContent(Elements blogPostElements) {
        return blogPostElements.select("p").stream().map(BlogPostService::buildBlogPostContent).toList();
    }

    private static String buildBlogPostContent(Element paragraph) {
        Elements paragraphImage = paragraph.select("img");

        if (paragraph.text().contains("Current Mood:")) {
            return paragraph.html();
        }

        if (!paragraphImage.isEmpty()) {
            if(paragraphImage.size() > 1) {
                StringBuilder sb = new StringBuilder();
                for(Element pImg : paragraphImage) {
                    sb.append(buildImageHtmlElement(pImg.attr("src"), pImg.attr("alt"), pImg.attr("width"), pImg.attr("height")));
                    sb.append(System.lineSeparator());
                }
                return sb.toString();
            }
            return buildImageHtmlElement(paragraphImage.attr("src"), paragraphImage.attr("alt"), paragraphImage.attr("width"), paragraphImage.attr("height"));
        }

        return cleanText(paragraph.html());
    }

    private static String cleanText(String text) {
        return text.trim()
                .replaceAll("[\\u200B\\u200C\\u200D\\uFEFF\\u00A0\\n\\r\\t\\f\\u0008]|&nbsp;|</?p>", "")
                .replaceAll("&amp;", "&")
                .replaceAll("\\s+", " ");
    }

    private static String buildImageHtmlElement(String src, String alt, String width, String height) {
        if(src.length() > 500) {
            LOGGER.info("paragraphImageSrcContent is too big, it will need to reprocess");
            return "";
        }

        return String.format("<img src=\"%s\" alt=\"%s\" width=\"%s\" height=\"%s\"/>", src, alt, width, height);
    }

    private static Optional<LocalDateTime> blogPostTheDateFormatter(String theDate) {
        if (theDate.contains("at")) {
            return blogPostTheDateWithHoursFormatter(theDate);
        }

        return blogPostTheDateWithoutHoursFormatter(theDate);
    }

    private static Optional<LocalDateTime> blogPostTheDateWithoutHoursFormatter(String theDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        LocalDateTime blogPostTheDateFormatted = LocalDateTime
                .of(LocalDate.parse(theDate, formatter),
                        LocalTime.of(0, 0, 0));

        return Optional.of(blogPostTheDateFormatted);
    }

    private static Optional<LocalDateTime> blogPostTheDateWithHoursFormatter(String theDate) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("MMMM d, yyyy")
                .appendLiteral(" at ")
                .appendPattern("h:mm a")
                .toFormatter(Locale.ENGLISH);

        try {
            LocalDateTime blogPostDate = LocalDateTime.parse(theDate, formatter);

            return Optional.of(blogPostDate);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private static Map<String, List<String>> getASOIAFBooksMentions(Elements blogPostElements) {
        Map<String, List<String>> ASOIAFBooksMentions = new HashMap<>();

        Elements blogPostParagraphs = blogPostElements.select("p");
        for (Element blogPostParagraph : blogPostParagraphs) {
            buildASOIAFBooksMentions(blogPostParagraph, ASOIAFBooksMentions);
        }

        return ASOIAFBooksMentions;
    }

    private static Map<String, List<String>> buildASOIAFBooksMentions(Element blogPostParagraph,
            Map<String, List<String>> ASOIAFBooksMentions) {
        List<List<String>> ASOIAFMentions = getOtherBooksList();

        for (List<String> ASOIAFMention : ASOIAFMentions) {
            if (containsFromList(blogPostParagraph.text(), ASOIAFMention)) {
                String key = ASOIAFMention.get(0);
                if (ASOIAFBooksMentions.containsKey(key)) {
                    ASOIAFBooksMentions.get(key).add(cleanText(blogPostParagraph.html()));
                } else {
                    List<String> mentions = new ArrayList<>();
                    mentions.add(cleanText(blogPostParagraph.html()));
                    ASOIAFBooksMentions.put(key, mentions);
                }
            }
        }

        return ASOIAFBooksMentions;
    }

    private static boolean containsFromList(String str, List<String> list) {
        for (String item : list) {
            if (cleanText(str).contains(item)) {
                return true;
            }
        }

        return false;
    }

    private static List<List<String>> getOtherBooksList() {
        return List.of(List.of("A GAME OF THRONES", "AGOT"),
                List.of("A CLASH OF KINGS", "CLASH OF KINGS", "CLASH", "ACOK"),
                List.of("A STORM OF SWORDS", "STORM OF SWORDS", "STORM", "ASOS"),
                List.of("A FEAST FOR CROWS", "FEAST FOR CROWS", "FEAST", "AFFC"),
                List.of("A DANCE WITH DRAGONS", "DANCE WITH DRAGONS", "DANCE", "ADWD"),
                List.of("THE WINDS OF WINTER", "WINDS OF WINTER", "TWOW"),
                List.of("A DREAM OF SPRING", "DREAM OF SPRING", "DREAM", "ADOS"),
                List.of("FIRE & BLOOD", "FIRE AND BLOOD", "F&B", "FAB"),
                List.of("BLOOD & FIRE", "BLOOD AND FIRE", "B&F", "BAF"),
                List.of("THE KNIGHT OF THE SEVEN KINGDOMS", "THE HEDGE KNIGHT", "THE SWORN SWORD",
                        "THE MISTERY KNIGHT"));

    }

}
