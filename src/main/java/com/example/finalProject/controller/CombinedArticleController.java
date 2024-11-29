package com.example.finalProject.controller;

import com.example.finalProject.dto.ArticleForm;
import com.example.finalProject.dto.MediaForm;
import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.Media;
import com.example.finalProject.entity.User;
import com.example.finalProject.service.CombinedArticleService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class CombinedArticleController {
    @Autowired
    CombinedArticleService combinedArticleService;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/articleNew";
    }

    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

    @PostMapping("/articles/upload")
    public String uploadArticle(ArticleForm articleForm,
                                @RequestParam("file") MultipartFile[] files,
                                HttpSession session,
                                Model model) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            model.addAttribute("error", "로그인이 필요합니다.");
            return "error";
        }

        // location 값이 비어있으면 null로 처리
        if (articleForm.getLocation() != null && articleForm.getLocation().trim().isEmpty()) {
            articleForm.setLocation(null);
        }

        try {
            // ArticleForm -> Article 변환 및 저장
            Article article = combinedArticleService.createArticle(articleForm, currentUser);

            // 파일 업로드 후 URL 및 파일 타입 정보 리스트 생성
            List<Map.Entry<String, String>> fileInfos = combinedArticleService.uploadFiles(files, currentUser.getUsername());

            // URL 및 파일 타입 정보를 기반으로 MediaForm 리스트 생성
            List<MediaForm> mediaForms = fileInfos.stream()
                    .map(entry -> {
                        MediaForm mediaForm = new MediaForm();
                        mediaForm.setFileUrl(entry.getKey()); // URL 설정
                        mediaForm.setFileType(entry.getValue()); // 파일 타입 설정
                        mediaForm.setArticleId(article.getId());
                        return mediaForm;
                    })
                    .toList();

            // MediaForm -> Media 변환 및 저장
            mediaForms.forEach(mediaForm -> combinedArticleService.createMedia(mediaForm));

            log.info("mediaForms: {}", mediaForms);

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }

        return "redirect:/main";
    }

    @PostMapping("/articles/update/{id}")
    public String updateArticles(@PathVariable("id") Long id,
                                 @ModelAttribute ArticleForm articleForm,
                                 @RequestParam("file") MultipartFile[] files) {
        // 대상 Article 가져오기
        Article target = combinedArticleService.showArticle(id);
        if (target == null) {
            throw new RuntimeException("Article not found");
        }

        // location 값이 비어있으면 null로 처리
        if (articleForm.getLocation() != null && articleForm.getLocation().trim().isEmpty()) {
            articleForm.setLocation(null);
        }

        // 업로드된 파일 정보 처리
        List<Map.Entry<String, String>> uploadedFileInfo = combinedArticleService.uploadFiles(files, target.getUser().getUsername());

        // 기존 MediaList 가져오기
        List<Media> existingMediaList = target.getMediaList();

        // 덮어씌우기 로직
        int index = 0;
        for (Map.Entry<String, String> entry : uploadedFileInfo) {
            if (index < existingMediaList.size()) {
                // 기존 미디어 수정
                Media media = existingMediaList.get(index);
                media.setFileUrl(entry.getKey());
                media.setFileType(entry.getValue());
            } else {
                // 새 미디어 추가
                Media newMedia = new Media();
                newMedia.setFileUrl(entry.getKey());
                newMedia.setFileType(entry.getValue());
                newMedia.setArticle(target);
                existingMediaList.add(newMedia);
            }
            index++;
        }

        // 남은 기존 MediaList가 더 많으면 잘라내기
        if (existingMediaList.size() > uploadedFileInfo.size()) {
            existingMediaList.subList(uploadedFileInfo.size(), existingMediaList.size()).clear();
        }

        // Article 업데이트
        combinedArticleService.updateArticle(id, articleForm);

        return "redirect:/main";
    }

    @GetMapping("/articles/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Article articleEntity = combinedArticleService.showArticle(id);
        model.addAttribute("article", articleEntity);
        log.info("article: {}", articleEntity);
        return "articles/articleEdit";
    }

    @GetMapping("/articles/delete/{id}")
    public String deleteArticles(@PathVariable("id") Long id) {
        combinedArticleService.deleteArticle(id);
        return "redirect:/main";
    }

}
