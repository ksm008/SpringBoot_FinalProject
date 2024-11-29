package com.example.finalProject.api;

import com.example.finalProject.dto.ArticleForm;
import com.example.finalProject.dto.MediaForm;
import com.example.finalProject.dto.UserForm;
import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.Media;
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

    @GetMapping("/api/users")
    public List<User> indexUser() {
        return userService.index();
    }

    @GetMapping("/api/users/{id}")
    public User showUsers(@PathVariable("id") Long id) {
        return userService.show(id);
    }

    @PostMapping("/api/users")
    public User createUsers(@RequestBody UserForm userForm) {
        return userService.register(userForm);
    }

    @PatchMapping("/api/users/{id}")
    public ResponseEntity<User> updateArticle(@PathVariable("id") Long id, @RequestBody UserForm userForm) {
        User updated = userService.update(id, userForm);

        return (updated != null) ? ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<User> deleteUsers(@PathVariable("id") Long id) {
        User deleted = userService.delete(id);

        return (deleted != null) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/api/articles")
    public List<Article> indexArticle() {
        return combinedArticleService.indexArticle();
    }

    @GetMapping("/api/articles/{id}")
    public Article showArticle(@PathVariable("id") Long id) {
        return combinedArticleService.showArticle(id);
    }

    @PostMapping("/api/articles")
    public Article createArticle(@RequestBody ArticleForm articleForm, HttpSession session) {
        User user = (User) session.getAttribute("user");
        log.info("user: {}", user);
        log.info("articleForm: {}", articleForm);
        return combinedArticleService.createArticle(articleForm, user);
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") Long id, @RequestBody ArticleForm articleForm) {
        Article updated = combinedArticleService.updateArticle(id, articleForm);

        return (updated != null) ? ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable("id") Long id) {
        Article deleted = combinedArticleService.deleteArticle(id);

        return (deleted != null) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/api/media")
    public List<Media> indexMedia() {
        return combinedArticleService.indexMedia();
    }

    @PostMapping("/api/media")
    public Media createMedia(@RequestBody MediaForm mediaForm) {
        return combinedArticleService.createMedia(mediaForm);
    }

    @GetMapping("/api/media/{id}")
    public Media showMedia(@PathVariable("id") Long id) {
        return combinedArticleService.showMedia(id);
    }

    @GetMapping("/api/media/articles/{articleId}")
    public List<Media> showSpecificMedia(@PathVariable("articleId") Long articleId) {
        return combinedArticleService.showSpecificMedia(articleId);
    }

    @PatchMapping("/api/media/{id}")
    public ResponseEntity<Media> updateMedia(@PathVariable("id") Long id, @RequestBody MediaForm mediaForm) {
        Media updated = combinedArticleService.updateMedia(id, mediaForm);

        return (updated != null) ? ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/media/{id}")
    public ResponseEntity<Media> deleteMedia(@PathVariable("id") Long id) {
        Media deleted = combinedArticleService.deleteMedia(id);

        return (deleted != null) ? ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
