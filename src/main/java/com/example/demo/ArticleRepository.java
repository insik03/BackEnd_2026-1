package com.example.demo;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ArticleRepository {

    private final EntityManager em;

    public ArticleRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void save(Article article) {
        em.persist(article);
    }

    public void update(Article article) {
        em.merge(article);
    }

    public Article findById(Long id) {
        return em.find(Article.class, id);
    }

    public List<Article> findAll() {
        return em.createQuery("select a from Article a", Article.class)
                .getResultList();
    }

    public void deleteById(Long id) {
        Article article = em.find(Article.class, id);
        if (article != null) {
            em.remove(article);
        }
    }
}