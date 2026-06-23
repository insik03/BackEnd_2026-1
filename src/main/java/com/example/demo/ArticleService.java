package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true) // 기본적으로 읽기 전용으로 설정
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public ArticleService(ArticleRepository articleRepository, MemberRepository memberRepository, BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public void createArticle(Article article) {
        articleRepository.save(article);
    }

    public List<ArticleResponse> getAllArticles() {
        return articleRepository.findAll().stream().map(article -> {
            return new ArticleResponse(
                    article.getId(),
                    article.getAuthorId(),
                    article.getBoardId(),
                    article.getTitle(),
                    article.getContent(),
                    article.getDate(),
                    article.getUpdateDate()
            );
        }).collect(Collectors.toList());
    }

    public ArticleResponse getArticleById(Long id) {
        Article article = articleRepository.findById(id);
        if (article == null) return null;

        return new ArticleResponse(
                article.getId(),
                article.getAuthorId(),
                article.getBoardId(),
                article.getTitle(),
                article.getContent(),
                article.getDate(),
                article.getUpdateDate()
        );
    }

    @Transactional
    public ArticleResponse updateArticle(Long id, Article updatedArticle) {
        Article article = articleRepository.findById(id);
        if (article == null) {
            throw new Exception404("해당 게시물을 찾을 수 없습니다.");
        }

        if (boardRepository.findById(updatedArticle.getBoardId()) == null ||
                memberRepository.findById(updatedArticle.getAuthorId()) == null) {
            throw new Exception400("존재하지 않는 사용자 혹은 게시판을 참조하고 있습니다.");
        }

        article.setTitle(updatedArticle.getTitle());
        article.setAuthorId(updatedArticle.getAuthorId());
        article.setBoardId(updatedArticle.getBoardId());
        article.setContent(updatedArticle.getContent());

        String currentTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        article.setUpdateDate(currentTime);

        articleRepository.save(article);
        return getArticleById(id);
    }

    @Transactional
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public List<ArticleResponse> getArticlesByBoardId(Long boardId) {
        return articleRepository.findAll().stream()
                .filter(a -> a.getBoardId().equals(boardId))
                .map(article -> new ArticleResponse(
                        article.getId(),
                        article.getAuthorId(),
                        article.getBoardId(),
                        article.getTitle(),
                        article.getContent(),
                        article.getDate(),
                        article.getUpdateDate()
                ))
                .collect(Collectors.toList());
    }
}