package com.example.finalProject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

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
    private User user;

    String content;
    String location;
    Date datePosted;

    public Article(Long id, String content, String location, Date datePosted) {
        this.id = id;
        this.content = content;
        this.location = location;
        this.datePosted = datePosted;
    }
}
