package com.minimal_not_a_bot.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Document(collection = "blog-post")
public class BlogPost {
    @Id 
    private String id;
    private String profileImage;
    private String title;
    @Indexed(unique = true) 
    private String url;
    private String theDate;
    private LocalDateTime theDateFormatted;
    private List<String> content;
    private String tags;
    private Map<String, List<String>> ASOIAFBooksMentions;
    private String postHashCode;
    private LocalDateTime insertTs;
}
