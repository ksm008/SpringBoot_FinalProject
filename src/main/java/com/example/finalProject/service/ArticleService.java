package com.example.finalProject.service;

import com.example.finalProject.dto.ArticleForm;
import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {
    Article saveArticle(ArticleForm articleForm, User user); // DTO를 받아 처리
    void uploadFiles(MultipartFile[] files, Article article); // 파일 업로드 처리
}
