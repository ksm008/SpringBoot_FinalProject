package com.example.finalProject.dto;


import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.Media;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class MediaForm {

    private Long id;
    private String fileUrl;
    private String fileType;
    @JsonProperty("articleId")
    private Long articleId;

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
