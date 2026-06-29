package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
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
        Long boardId = article.getBoard() != null ? article.getBoard().getId() : null;
        if (boardId != null) {
            // 2. DB에서 진짜 영속 상태의 Board 엔티티를 찾아옵니다.
            Board realBoard = boardRepository.findById(boardId)
                    .orElseThrow(() -> new Exception404("해당 게시판을 찾을 수 없습니다."));
            article.setBoard(realBoard);
            realBoard.getArticles().add(article);
        }
        articleRepository.save(article);
    }

    public List<ArticleResponse> getAllArticles() {
        return articleRepository.findAll().stream().map(article -> {
            return new ArticleResponse(
                    article.getId(),
                    article.getAuthorId(),
                    article.getBoard() != null ? article.getBoard().getId() : null,
                    article.getTitle(),
                    article.getContent(),
                    article.getDate(),
                    article.getUpdateDate()
            );
        }).collect(Collectors.toList());
    }

    public ArticleResponse getArticleById(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) return null;

        return new ArticleResponse(
                article.getId(),
                article.getAuthorId(),
                article.getBoard() != null ? article.getBoard().getId() : null,
                article.getTitle(),
                article.getContent(),
                article.getDate(),
                article.getUpdateDate()
        );
    }

    @Transactional
    public ArticleResponse updateArticle(Long id, Article updatedArticle) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 게시물을 찾을 수 없습니다."));

        Long newBoardId = updatedArticle.getBoard() != null ? updatedArticle.getBoard().getId() : null;
        Board newBoard = null;
        if (newBoardId != null) {
            newBoard = boardRepository.findById(newBoardId)
                    .orElseThrow(() -> new Exception400("존재하지 않는 게시판을 참조하고 있습니다."));
        }

        if (memberRepository.findById(updatedArticle.getAuthorId()).isEmpty()) {
            throw new Exception400("존재하지 않는 사용자를 참조하고 있습니다.");
        }

        article.setTitle(updatedArticle.getTitle());
        article.setAuthorId(updatedArticle.getAuthorId());
        article.setBoard(newBoard);
        article.setContent(updatedArticle.getContent());

        String currentTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        article.setUpdateDate(currentTime);
        return getArticleById(id);
    }

    @Transactional
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public List<ArticleResponse> getArticlesByBoardId(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new Exception404("해당 게시판을 찾을 수 없습니다."));

        return board.getArticles().stream()
                .map(article -> new ArticleResponse(
                        article.getId(),
                        article.getAuthorId(),
                        article.getBoard() != null ? article.getBoard().getId() : null,
                        article.getTitle(),
                        article.getContent(),
                        article.getDate(),
                        article.getUpdateDate()
                ))
                .collect(Collectors.toList());
    }
}