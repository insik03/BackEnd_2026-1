package com.example.demo;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleRepository {
    private final Map<Long, Article> articleMap = new HashMap<>();

    public void save(Article article) {
        articleMap.put(article.getId(), article);
    }

    public Article findById(Long id) {
        return articleMap.get(id);
    }

    public List<Article> findAll() {
        return new ArrayList<>(articleMap.values());
    }

    public void deleteById(Long id) {
        articleMap.remove(id);
    }
}