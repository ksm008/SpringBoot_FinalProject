package com.example.finalProject.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@ToString
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "articleId")
    Long articleId;
    @Column(name = "userId")
    Long userId;
    String comment;
}
