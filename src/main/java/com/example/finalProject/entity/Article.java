package com.example.finalProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@ToString
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    String content;
    String location;
    Date datePosted;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Media> mediaList = new ArrayList<>();

    public Article(Long id, String content, String location, Date datePosted) {
        this.id = id;
        this.content = content;
        this.location = location;
        this.datePosted = datePosted;
    }

    public void patch(Article article) {
        if(article.content != null){
            this.content = article.content;
        }
        if(article.location != null) {
            this.location = article.location;
        }
        if(article.datePosted != null) {
            this.datePosted = article.datePosted;
        }
    }
}
