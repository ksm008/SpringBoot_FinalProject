package com.example.finalProject.controller;

import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.User;
import com.example.finalProject.repository.ArticleRepository;
import com.example.finalProject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/articleNew";
    }

//    @PostMapping("/articles/create")
//    public String createArticle(ArticleForm articleForm){
//        articleForm.logInfo();
//        //1. DTO를 Entity로 변환
//        Article article = articleForm.toEntity();
//        article.logInfo();
//        //2. repository로 엔터티를 저장
//        Article saved = articleRepository.save(article);
//        saved.logInfo();
//        return "redirect:/articles/" + saved.getId();
//    }
}
