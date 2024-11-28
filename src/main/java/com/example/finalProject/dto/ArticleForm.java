package com.example.finalProject.dto;

import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.Media;
import com.example.finalProject.entity.User;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j

public class ArticleForm {
    private Long id;
    private Long userId;
    private String content;
    private String location;
    private Date datePosted;

    private List<Media> mediaList;

    public Article toEntity() {
        User user = null;

        // userId가 null이 아니면 User 객체 생성
        if (userId != null) {
            user = new User();
            user.setId(userId); // userId를 설정
        }

        // Article 엔티티 생성
        Article article = new Article(id, content, location, datePosted, user, mediaList);

        // mediaList를 Media 엔티티로 변환 및 설정
        if (mediaList != null) {
            List<Media> mediaEntities = mediaList.stream()
                    .map(media -> {
                        // Media 객체에 Article 연관 설정
                        media.setArticle(article);
                        return media;
                    })
                    .toList();
            article.setMediaList(mediaEntities);
        }

        return article;
    }

    public void logInfo() {
        log.info("content: {}, location : {}, datePosted = {}", content, location, datePosted);
    }

}
