package com.example.finalProject.dto;

import com.example.finalProject.entity.Article;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j

public class ArticleForm {
    private Long id;
    private String content;
    private String location;
    private Date datePosted;

    public Article toEntity() {
        return new Article(id, content, location, datePosted);
    }

    public void logInfo() {
        log.info("title: {}, content: {}, location : {}, datePosted = {}", content, location, datePosted);
    }

}
