package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public ArticleService(ArticleRepository articleRepository, MemberRepository memberRepository, BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public void createArticle(Article article) {
        articleRepository.save(article);
    }

    public List<ArticleResponse> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleResponse> responseList = new ArrayList<>();

        for (Article article : articles) {
            Member member = memberRepository.findById(article.getMemberId());
            String authorName = (member != null) ? member.getName() : "알 수 없음";

            ArticleResponse response = new ArticleResponse(
                    article.getTitle(),
                    authorName,
                    article.getDate(),
                    article.getContent()
            );
            responseList.add(response);
        }
        return responseList;
    }

    public ArticleResponse getArticleById(Long id) {
        Article article = articleRepository.findById(id);
        if (article == null) return null;

        Member member = memberRepository.findById(article.getMemberId());
        String authorName = (member != null) ? member.getName() : "알 수 없음";

        return new ArticleResponse(article.getTitle(), authorName, article.getDate(), article.getContent());
    }

    public void updateArticle(Long id, Article updatedArticle) {
        Article article = articleRepository.findById(id);
        if (article != null) {
            article.setTitle(updatedArticle.getTitle());
            article.setMemberId(updatedArticle.getMemberId());
            article.setBoardId(updatedArticle.getBoardId());
            article.setContent(updatedArticle.getContent());
            article.setUpdateDate(updatedArticle.getUpdateDate());
        }
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}