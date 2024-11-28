package com.example.finalProject.dto;


import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.Media;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class MediaForm {

    Long id;
    String fileUrl;
    String fileType;
    Long articleId;

    public Media toEntity() {
        Article article = null;

        if (articleId != null) {
            article = new Article();
            article.setId(articleId);
        }
        return new Media(fileUrl, fileType, article);
    }

    public void logInfo() {
        log.info("id: {}, fileUrl: {}, fileType: {}", id, fileUrl, fileType);
    }
}
