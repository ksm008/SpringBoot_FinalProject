package com.example.finalProject.service;

import com.example.finalProject.dto.ArticleForm;
import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CombinedArticleService {
    List<Article> index();
    Article show(Long id);
    Article create(ArticleForm articleForm, User user);
    Article update(Long id, ArticleForm articleForm);
    Article delete(Long id);
    void uploadFiles(MultipartFile[] files, Article article);
}
