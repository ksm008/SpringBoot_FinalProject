package com.example.finalProject.service;

import com.example.finalProject.dto.ArticleForm;
import com.example.finalProject.dto.MediaForm;
import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.Media;
import com.example.finalProject.entity.User;
import com.example.finalProject.repository.ArticleRepository;
import com.example.finalProject.repository.MediaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CombinedArticleServiceImpl implements CombinedArticleService {
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Override
    public List<Article> index() {
        return articleRepository.findAll();
    }

    @Override
    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public Article create(ArticleForm articleForm, User user) {
        log.info("Service User: {}", user);
        log.info("ArticleForm: {}", articleForm);
        Article article = articleForm.toEntity();
        article.setUser(user);
        article.setDatePosted(new Date());
        return articleRepository.save(article);
    }

    @Override
    public Article update(Long id, ArticleForm articleForm) {
        articleForm.setId(-1L);

        Article article = articleForm.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        Article target = articleRepository.findById(id).orElse(null);

        if (target == null || id != target.getId()) {
            log.info("잘못된 요청");
            return null;
        }

        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    @Override
    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);

        if (target == null) {
            log.info("해당 Article이 없습니다.");
            return null;
        }

        articleRepository.delete(target);

        return target;
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
