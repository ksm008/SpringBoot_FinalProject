package com.example.finalProject.controller;

import com.example.finalProject.dto.ArticleForm;
import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.User;
import com.example.finalProject.service.CombinedArticleService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
public class ArticleController {
    @Autowired
    CombinedArticleService combinedArticleService;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/articleNew";
    }

    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

    @PostMapping("/articles/upload")
    public String uploadArticle(ArticleForm articleForm,
                                @RequestParam("file") MultipartFile[] files,
                                HttpSession session,
                                Model model) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            model.addAttribute("error", "로그인이 필요합니다.");
            return "error";
        }

        try {
            // ArticleForm -> Article 변환 및 저장
            Article article = combinedArticleService.create(articleForm, currentUser);

            // 파일 업로드 처리
            combinedArticleService.uploadFiles(files, article);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }

        return "redirect:/main";
    }

}
