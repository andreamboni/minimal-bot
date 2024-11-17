package com.minimal_not_a_bot.model;

import java.util.List;

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
public class BlogPost {
    private String postImage;
    private String title;
    private String url;
    private String theDate;
    private List<String> content;
    private List<String> mentionsOfNextBook;
}
