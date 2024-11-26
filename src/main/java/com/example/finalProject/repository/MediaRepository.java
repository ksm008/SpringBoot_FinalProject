package com.example.finalProject.repository;

import com.example.finalProject.entity.Article;
import com.example.finalProject.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByArticleId(Long id);
}
