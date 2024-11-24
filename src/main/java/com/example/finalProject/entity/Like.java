package com.example.finalProject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "Likes")
@ToString
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "articleId", referencedColumnName = "id")
    private Article article;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    boolean isLiked;

}
