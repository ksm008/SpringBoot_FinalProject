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
    @Column(name = "userId")
    Long userId;
    String title;
    String content;
    String location;
    @Column(name = "datePosted")
    Date datePosted;
}
