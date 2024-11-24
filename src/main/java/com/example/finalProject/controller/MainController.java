package com.example.finalProject.controller;

import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.User;
import com.example.finalProject.repository.ArticleRepository;
import com.example.finalProject.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    ArticleRepository articleRepository;

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

        model.addAttribute("articleList", articleList);
        return "articles/main";
    }
}
