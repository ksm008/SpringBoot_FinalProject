package com.example.finalProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@ToString(exclude = "article")
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "fileUrl")
    String fileUrl;
    @Column(name = "fileType")
    String fileType;

    @ManyToOne
    @JoinColumn(name = "articleId", referencedColumnName = "id")
    @JsonIgnore
    private Article article;

    public void patch(Media media) {
        if(media.getFileUrl() != null){
            this.fileUrl = media.getFileUrl();
        }
        if(media.getFileType() != null){
            this.fileType = media.getFileType();
        }
    }

    public Long getArticleId() {
        return this.article != null ? this.article.getId() : null;
    }

    public Media(String fileUrl, String fileType, Article article) {
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.article = article;
    }
}
