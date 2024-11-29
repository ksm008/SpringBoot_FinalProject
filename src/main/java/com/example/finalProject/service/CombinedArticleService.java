package com.example.finalProject.service;

import com.example.finalProject.dto.ArticleForm;
import com.example.finalProject.dto.MediaForm;
import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.Media;
import com.example.finalProject.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CombinedArticleService {
    List<Article> indexArticle();
    Article showArticle(Long id);
    Article createArticle(ArticleForm articleForm, User user);
    Article updateArticle(Long id, ArticleForm articleForm);
    Article deleteArticle(Long id);
    List<Map.Entry<String, String>> uploadFiles(MultipartFile[] files, String username);

    List<Media> indexMedia();
    Media showMedia(Long id);
    List<Media> showSpecificMedia(Long id);
    Media createMedia(MediaForm mediaForm);
    Media updateMedia(Long id, MediaForm mediaForm);
    Media deleteMedia(Long id);
}
