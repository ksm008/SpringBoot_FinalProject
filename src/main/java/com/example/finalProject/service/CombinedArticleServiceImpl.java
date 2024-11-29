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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CombinedArticleServiceImpl implements CombinedArticleService {
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    MediaRepository mediaRepository;

    @Override
    public List<Article> indexArticle() {

        // 모든 Article 가져오기
        List<Article> articles = articleRepository.findAll();

        // 각 Article에 mediaList 설정
        articles.forEach(article -> {
            List<Media> mediaList = mediaRepository.findByArticle_Id(article.getId());
            article.setMediaList(mediaList); // Article에 Media 리스트 설정
            log.info("mediaList: {}", mediaList);
        });
        return articles;
    }

    @Override
    public Article showArticle(Long id) {
        Article article = articleRepository.findById(id).orElse(null);

        if (article != null) {
            // 특정 Article에 연결된 Media 리스트 가져오기
            List<Media> mediaList = mediaRepository.findByArticle_Id(id);
            article.setMediaList(mediaList); // Article에 Media 리스트 설정
        }
        log.info("Total Article : {}", article);

        return article; // Media가 설정된 Article 반환
    }

    @Override
    public Article createArticle(ArticleForm articleForm, User user) {
        log.info("Service User: {}", user);
        log.info("ArticleForm: {}", articleForm);
        Article article = articleForm.toEntity();
        article.setUser(user);
        article.setDatePosted(new Date());
        return articleRepository.save(article);
    }

    @Override
    public Article updateArticle(Long id, ArticleForm articleForm) {
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
    public Article deleteArticle(Long id) {
        Article target = articleRepository.findById(id).orElse(null);

        if (target == null) {
            log.info("해당 Article이 없습니다.");
            return null;
        }

        articleRepository.delete(target);

        return target;
    }

    @Override
    public List<Map.Entry<String, String>> uploadFiles(MultipartFile[] files, String username) {
        String userDirectory = UPLOAD_DIR + username;
        Path userPath = Paths.get(userDirectory);
        List<Map.Entry<String, String>> uploadedFileInfo = new ArrayList<>();

        try {
            if (!Files.exists(userPath)) {
                Files.createDirectories(userPath);
            }

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    Path filePath = userPath.resolve(fileName);
                    file.transferTo(filePath.toFile());

                    String fileUrl = "/uploads/" + username + "/" + fileName;
                    String fileType = file.getContentType();

                    // 파일 URL과 타입을 리스트에 추가
                    uploadedFileInfo.add(Map.entry(fileUrl, fileType));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패: " + e.getMessage());
        }

        log.info("Uploaded file info: {}", uploadedFileInfo);
        return uploadedFileInfo; // 업로드된 파일 정보 반환
    }

    @Override
    public List<Media> indexMedia() {
        return mediaRepository.findAll();
    }

    @Override
    public Media showMedia(Long id) {
        return mediaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Media> showSpecificMedia(Long id) {
        return mediaRepository.findByArticle_Id(id);
    }

    @Override
    public Media createMedia(MediaForm mediaForm) {
        Article article = articleRepository.findById(mediaForm.getArticleId())
                .orElseThrow(() -> new RuntimeException("Article not found"));

        Media media = mediaForm.toEntity();
        media.setArticle(article);
        log.info("Media: {}", media);
        return mediaRepository.save(media);
    }

    @Override
    public Media updateMedia(Long id, MediaForm mediaForm) {
        mediaForm.setId(-1L);

        Media media = mediaForm.toEntity();

        Media target = mediaRepository.findById(id).orElse(null);

        if (target == null || id != target.getId()) {
            return null;
        }

        target.patch(media);
        Media updated = mediaRepository.save(target);

        return updated;
    }

    @Override
    public Media deleteMedia(Long id) {
        Media target = mediaRepository.findById(id).orElse(null);

        if (target == null) {
            log.info("해당 Media가 없습니다.");
            return null;
        }

        mediaRepository.delete(target);

        return target;
    }
}
