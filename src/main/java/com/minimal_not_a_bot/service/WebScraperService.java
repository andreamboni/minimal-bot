package com.minimal_not_a_bot.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.minimal_not_a_bot.model.BlogPost;
import com.minimal_not_a_bot.util.HashUtil;

@Service
public class WebScraperService {
    private static final Logger LOGGER = LogManager.getLogger(WebScraperService.class);

    private static final String URL = "https://georgerrmartin.com/notablog/";

    public BlogPost getLastBlogPost() {
        try (WebClient webClient = new WebClient()) {
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(true);

            HtmlPage page = webClient.getPage(URL);

            List<HtmlElement> posts = page.getByXPath("//div[@class='post-main']");

            if (posts != null && !posts.isEmpty()) {
                return buildBlogPost(posts.get(0));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<BlogPost> getBlogPosts(String url) {
        List<BlogPost> allBlogPosts = new ArrayList<>();

        try (WebClient webClient = new WebClient()) {
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(true);

            HtmlPage page = webClient.getPage(url);

            List<HtmlElement> posts = page.getByXPath("//div[@class='post-main']");

            for (HtmlElement post : posts) {
                allBlogPosts.add(buildBlogPost(post));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return allBlogPosts;
    }

    public boolean mentionsNextBook(String content, String... nextBookName) {
        for (String bookName : nextBookName) {
            if (content.contains(bookName)) {
                return true;
            }
        }
        return false;
    }

    private BlogPost buildBlogPost(HtmlElement post) {
        HtmlImage postImageElement = post.querySelector("img");
        String postImage = postImageElement != null ? postImageElement.getSrcAttribute() : "";

        HtmlElement titleElement = post.querySelector("h1");
        String title = titleElement != null ? titleElement.asNormalizedText() : "";

        HtmlAnchor urlElement = post.querySelector("a");
        String postUrl = urlElement != null ? urlElement.getHrefAttribute() : "";

        HtmlElement dateElement = post.querySelector(".thedate");
        String theDate = dateElement != null ? dateElement.asNormalizedText() : "";

        String postHashCode = HashUtil.generateHash(title, theDate, postUrl);

        HtmlElement contentContainer = post.querySelector(".post");
        
        List<String> content = new ArrayList<>();
        List<String> mentionsOfNextBook = new ArrayList<>();
        String tags = null;

        if (contentContainer != null) {
            List<HtmlElement> paragraphs = contentContainer.getByXPath(".//p");

            for (HtmlElement paragraph : paragraphs) {
                HtmlImage img = paragraph.querySelector("img");
                HtmlElement iframe = paragraph.querySelector("iframe");

                if (img != null) {
                    content.add(img.getSrcAttribute());
                } else if (iframe != null) {
                    content.add(iframe.getAttribute("src"));
                } else {
                    String preContent = paragraph.asNormalizedText();
                    // String cleanContent = preContent.replaceAll("[^\\p{Print}\\p{Space}]|&nbsp;|</?p>|&amp", "");
                    String cleanContent = preContent.replaceAll("[\\u200B\\u200C\\u200D\\uFEFF\\u00A0\\n\\r\\t\\f\\u0008]|&nbsp;", "");
                    if (!cleanContent.isEmpty()) {
                        content.add(cleanContent);
                        LOGGER.debug("Content for the title {}: {}", title, cleanContent);
                        if (mentionsNextBook(cleanContent, "WINDS OF WINTER", "THE WINDS OF WINTER", "TWOW")) {
                            mentionsOfNextBook.add(cleanContent);
                        }
                    }
                }

            }
            

            HtmlElement categories = contentContainer.querySelector(".categories");
            if(categories != null) {
                HtmlElement tagi = categories.querySelector(".tagi");
                if(tagi != null) {
                    tags = tagi.asNormalizedText();
                }
            }

        }

        return BlogPost
                .builder()
                .postImage(postImage)
                .title(title)
                .url(postUrl)
                .theDate(theDate)
                .content(content)
                .tags(tags)
                .mentionsOfNextBook(mentionsOfNextBook)
                .numberOfMentionsOfNextBook(mentionsOfNextBook.size())
                .postHashCode(postHashCode)
                .insertTs(LocalDateTime.now())
                .build();
    }

    // private String buildContent(List<String> contentList) {
    //     StringBuilder content = new StringBuilder();
    //     for(String c : contentList) {
    //         content.append(c);
    //         content.append(System.lineSeparator());
    //     }

    //     return content.toString();
    // }

    // private String buildMentions(List<String> mentionsOfNextBook) {
    //     StringBuilder mentions = new StringBuilder();
    //     int mentionNumber = 1;
    //     for(String m : mentionsOfNextBook) {
    //         mentions.append("Mention number " + mentionNumber);
    //         mentions.append(m);
    //         mentions.append(System.lineSeparator());
    //     }
    //     return mentions.toString();
    // }

}
