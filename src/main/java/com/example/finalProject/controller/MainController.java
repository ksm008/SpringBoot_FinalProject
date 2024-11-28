package com.example.finalProject.controller;

import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.Media;
import com.example.finalProject.entity.User;
import com.example.finalProject.repository.ArticleRepository;
import com.example.finalProject.repository.MediaRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class MainController {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    MediaRepository mediaRepository;

    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            // "/uploads/**" 경로를 실제 파일 시스템 경로에 매핑
            registry.addResourceHandler("/uploads/**")
                    .addResourceLocations("file:" + System.getProperty("user.dir") + "/src/main/resources/static/uploads/");
        }
    }

    @GetMapping("/main")
    public String mainPage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("user");

        if (loggedInUser != null) {
            model.addAttribute("logedIn", true);
            model.addAttribute("username", loggedInUser.getUsername());
        } else {
            model.addAttribute("logedIn", false);
        }

        List<Article> articleList = articleRepository.findAll();
        List<Map<String, Object>> articlesWithAuthor = new ArrayList<>();

        articleList.forEach(article -> {
            Map<String, Object> articleWithAuthor = new HashMap<>();
            articleWithAuthor.put("article", article);
            articleWithAuthor.put("isAuthor", loggedInUser != null && loggedInUser.getId().equals(article.getUser().getId()));

            articlesWithAuthor.add(articleWithAuthor);
        });
        model.addAttribute("articlesWithAuthor", articlesWithAuthor);
//        model.addAttribute("articleList", articleList);
        return "articles/main";
    }
}
