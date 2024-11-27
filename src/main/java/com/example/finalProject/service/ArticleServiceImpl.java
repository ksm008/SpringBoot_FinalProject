package com.example.finalProject.service;

import com.example.finalProject.dto.ArticleForm;
import com.example.finalProject.dto.MediaForm;
import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.Media;
import com.example.finalProject.entity.User;
import com.example.finalProject.repository.ArticleRepository;
import com.example.finalProject.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Override
    public Article saveArticle(ArticleForm articleForm, User user) {
        // DTO를 엔티티로 변환
        Article article = articleForm.toEntity();
        article.setUser(user); // User 정보 설정
        article.setDatePosted(new Date()); // 게시 날짜 설정
        return articleRepository.save(article);
    }

    @Override
    public void uploadFiles(MultipartFile[] files, Article article) {
        String userDirectory = UPLOAD_DIR + article.getUser().getUsername();
        Path userPath = Paths.get(userDirectory);

        try {
            if (!Files.exists(userPath)) {
                Files.createDirectories(userPath);
            }

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    Path filePath = userPath.resolve(fileName);
                    file.transferTo(filePath.toFile());

                    // Media 엔티티 생성
                    MediaForm mediaForm = new MediaForm();
                    mediaForm.setFileUrl("/uploads/" + article.getUser().getUsername() + "/" + fileName);
                    mediaForm.setFileType(file.getContentType());
                    mediaForm.setUploadTime(new Date());
                    mediaForm.logInfo();

                    Media media = mediaForm.toEntity();
                    media.setArticle(article);
                    mediaRepository.save(media);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패: " + e.getMessage());
        }
    }
}
