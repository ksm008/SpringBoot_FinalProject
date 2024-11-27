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
    @Column(name = "fileUrl")
    String fileUrl;
    @Column(name = "fileType")
    String fileType;
    @Column(name = "uploadTime")
    Date uploadTime;

    @ManyToOne
    @JoinColumn(name = "articleId", referencedColumnName = "id")
    private Article article;

    public Media(Long id, String fileUrl, String fileType, Date uploadTime) {
    }
}
