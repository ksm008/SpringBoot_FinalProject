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

public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "articleId")
    Long articleId;
    @Column(name = "fileUrl")
    String fileUrl;
    @Column(name = "fileType")
    String fileType;
    @Column(name = "uploadTime")
    Date uploadtime;
}
