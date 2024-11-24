package com.example.finalProject.controller;

import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.Media;
import com.example.finalProject.entity.User;
import com.example.finalProject.repository.ArticleRepository;
import com.example.finalProject.repository.MediaRepository;
import com.example.finalProject.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MediaRepository mediaRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/articleNew";
    }

    private final String UPLOAD_DIR = System.getProperty("user.dir") + "src/main/resources/static/uploads/";

    @PostMapping("/articles/upload")
    public String uploadArticle(@RequestParam("file") MultipartFile[] files,
                                @RequestParam("content") String content,
                                HttpSession session,
                                Model model){

        User currentUser = (User) session.getAttribute("user");
        String userDirectory = UPLOAD_DIR + currentUser.getUsername();

        Article article = new Article();
        article.setContent(content);
        article.setDatePosted(new Date());
        article.setUser(currentUser);
        articleRepository.save(article);

        Path userPath = Paths.get(userDirectory);
        if (!Files.exists(userPath)) {
            try {
                Files.createDirectories(userPath);
            } catch (IOException e) {
                model.addAttribute("error", "디렉토리 생성 실패!");
                return "error";
            }
        }

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    // 파일 경로 생성
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    Path filePath = userPath.resolve(fileName);

                    // 파일 저장
                    file.transferTo(filePath.toFile());

                    // Media 엔티티 생성 및 저장
                    Media media = new Media();
                    media.setArticleId(article.getId());
                    media.setFileUrl(userDirectory + "/" + fileName);
                    media.setFileType(file.getContentType());
                    media.setUploadtime(new Date());
                    mediaRepository.save(media);

                } catch (IOException e) {
                    // 에러 로그 출력
                    e.printStackTrace();
                    model.addAttribute("error", "파일 저장 실패: " + e.getMessage());
                    return "error";
                } catch (Exception e) {
                    // 데이터베이스 또는 기타 문제 처리
                    e.printStackTrace();
                    model.addAttribute("error", "데이터 처리 중 오류 발생: " + e.getMessage());
                    return "error";
                }
            }
        }


        return "redirect:/main";
    }
}
