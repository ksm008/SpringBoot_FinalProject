package com.example.finalProject.api;

import com.example.finalProject.repository.ArticleRepository;
import com.example.finalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.User;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApiController {

    @Autowired
    UserRepository userRepository;
    ArticleRepository articleRepository;

    @GetMapping("/api/usernames")
    public List<String> getUsernames() {
        List<String> usernames = userRepository.findAll()
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        return usernames;
    }
}
