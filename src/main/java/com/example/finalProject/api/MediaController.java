package com.example.finalProject.api;

import com.example.finalProject.dto.MediaForm;
import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.Media;
import com.example.finalProject.repository.ArticleRepository;
import com.example.finalProject.repository.MediaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class MediaController {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private ArticleRepository articleRepository;

    // 모든 Media 조회
    @GetMapping("/api/media")
    public ResponseEntity<List<Media>> getAllMedia() {
        List<Media> mediaList = mediaRepository.findAll();
        return ResponseEntity.ok(mediaList);
    }

    // 특정 Media 조회
    @GetMapping("/api/media/{id}")
    public ResponseEntity<Media> getMediaById(@PathVariable Long id) {
        Media media = mediaRepository.findById(id).orElse(null);
        if (media == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(media);
    }

    // 특정 Article의 Media 조회
    @GetMapping("/api/media/article/{articleId}")
    public ResponseEntity<List<Media>> getMediaByArticle(@PathVariable Long articleId) {
        Article article = articleRepository.findById(articleId).orElse(null);
        if (article == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(article.getMediaList());
    }

    // Media 생성
    /*
    {
    "id": 1,
    "fileUrl": "/path/to/file.jpg",
    "fileType": "image/jpeg",
    "uploadTime": "2023-11-27T12:34:56.789Z",
    "articleId": 1
    }   
     */
    @PostMapping("/api/media")
    public ResponseEntity<Media> createMedia(@RequestBody MediaForm mediaForm) {
        Long articleId = mediaForm.getArticleId();
        Article article = articleRepository.findById(articleId).orElse(null);

        if (article == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Media media = mediaForm.toEntity();
        media.setArticle(article);

        Media savedMedia = mediaRepository.save(media);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMedia);
    }

    // Media 수정   
    /*
    {
    "id": 1,
    "fileUrl": "/new/path/to/file.jpg",
    "fileType": "image/jpeg",
    "uploadTime": "2023-11-27T12:34:56.789Z"
    }
     */
    @PatchMapping("/api/media/{id}")
    public ResponseEntity<Media> updateMedia(@PathVariable Long id, @RequestBody MediaForm mediaForm) {
        Media targetMedia = mediaRepository.findById(id).orElse(null);
        if (targetMedia == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        targetMedia.patch(mediaForm.toEntity());
        Media updatedMedia = mediaRepository.save(targetMedia);
        
        return ResponseEntity.ok(updatedMedia);
    }
    // Media 삭제
    @DeleteMapping("/api/media/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long id) {
        Media targetMedia = mediaRepository.findById(id).orElse(null);
        if (targetMedia == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        mediaRepository.delete(targetMedia);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}