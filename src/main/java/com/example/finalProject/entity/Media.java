package com.example.finalProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Article article;

    public Media(Long id, String fileUrl, String fileType, Date uploadTime) {
        this.id = id;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.uploadTime = uploadTime;
    }

    public void patch(Media media) {
        if(media.getFileUrl() != null){
            this.fileUrl = media.getFileUrl();
        }
        if(media.getFileType() != null){
            this.fileType = media.getFileType();
        }
        if(media.getUploadTime() != null){
            this.uploadTime = media.getUploadTime();
        }

    }

    public void loginfo(){
        log.info("id: " + id, "fileUrl: " + fileUrl, "fileType: " + fileType, "uploadTime: " + uploadTime);
    }
}
