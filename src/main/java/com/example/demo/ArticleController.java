package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final Map<Long, Article> articleMap = new HashMap<>();

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        articleMap.put(1L, article);
        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        if (!articleMap.containsKey(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articleMap.get(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article updatedArticle) {
        if (!articleMap.containsKey(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Article article = articleMap.get(id);
        article.setDescription(updatedArticle.getDescription());

        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        if (!articleMap.containsKey(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        articleMap.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}