package com.example.finalProject.api;

import com.example.finalProject.dto.ArticleForm;
import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.User;
import com.example.finalProject.service.CombinedArticleService;
import com.example.finalProject.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ApiController {
    @Autowired
    UserService userService;
    @Autowired
    CombinedArticleService combinedArticleService;

    //
    @PostMapping("/api/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> loginData, HttpSession session) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        try {
            User user = userService.login(username, password);

            session.setAttribute("user", user);

            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/api/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        userService.logout(session);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/articles")
    public List<Article> index() {
        return combinedArticleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable("id") Long id) {
        return combinedArticleService.show(id);
    }

    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm articleForm, HttpSession session) {
        User user = (User) session.getAttribute("user");
        log.info("user: {}", user);
        log.info("articleForm: {}", articleForm);
        return combinedArticleService.create(articleForm, user);
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable("id") Long id, @RequestBody ArticleForm articleForm) {
        Article updated = combinedArticleService.update(id, articleForm);

        return (updated != null) ? ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable("id") Long id) {
        Article deleted = combinedArticleService.delete(id);

        return (deleted != null) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
