package com.minimal_not_a_bot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.minimal_not_a_bot.model.BlogPost;

@Service
public class WebScraperService {

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

    public List<BlogPost> getBlogPosts() {
        List<BlogPost> allBlogPosts = new ArrayList<>();

        try (WebClient webClient = new WebClient()) {
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(true);

            HtmlPage page = webClient.getPage(URL);

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

        HtmlElement contentContainer = post.querySelector(".post");
        List<String> content = new ArrayList<>();
        List<String> mentionsOfNextBook = new ArrayList<>();

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
                    String cleanContent = paragraph.asXml().replaceAll("[^\\p{Print}\\p{Space}]|&nbsp;|</?p>|&amp", "");
                    if (!cleanContent.isEmpty()) {
                        content.add(cleanContent);

                        if (mentionsNextBook(cleanContent, "WINDS OF WINTER", "THE WINDS OF WINTER", "TWOW")) {
                            mentionsOfNextBook.add(cleanContent);
                        }
                    }
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
                .mentionsOfNextBook(mentionsOfNextBook)
                .build();
    }

}
