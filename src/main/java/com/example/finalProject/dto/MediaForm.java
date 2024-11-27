package com.example.finalProject.dto;


import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.Media;
import com.example.finalProject.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class MediaForm {

    Long id;
    String fileUrl;
    String fileType;
    Date uploadTime;

    public Media toEntity() {
        return new Media(id, fileUrl, fileType, uploadTime);
    }

    public void logInfo() {
        log.info("id: {}, fileUrl: {}, fileType: {}, uploadTime : {}", id, fileUrl, fileType, uploadTime);
    }
}
