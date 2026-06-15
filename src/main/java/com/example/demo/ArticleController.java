package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class ArticleController {

    private final ArticleService articleService;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public ArticleController(ArticleService articleService, MemberRepository memberRepository, BoardRepository boardRepository) {
        this.articleService = articleService;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    @GetMapping("/posts")
    public String getPostsPage(Model model) {
        List<ArticleResponse> articles = articleService.getAllArticles();
        model.addAttribute("articles", articles);
        model.addAttribute("boardName", "자유게시판");
        return "posts";
    }

    @PostMapping("/articles")
    @ResponseBody
    public ResponseEntity<String> createPostFromPostman(@Valid @RequestBody Article input) {
        if (boardRepository.findById(input.getBoardId()) == null || memberRepository.findById(input.getMemberId()) == null) {
            throw new Exception400("존재하지 않는 사용자 혹은 게시판을 참조하고 있습니다.");
        }

        if (input.getDate() == null || input.getDate().isEmpty()) {
            String currentTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS+00:00"));
            input.setDate(currentTime);
            input.setUpdateDate(currentTime);
        }

        articleService.createArticle(input);
        return new ResponseEntity<>("게시글 등록 완료!", HttpStatus.CREATED);
    }

    @GetMapping("/articles")
    @ResponseBody
    public ResponseEntity<List<ArticleResponse>> getArticlesByBoardId(@RequestParam(required = false) Long boardId) {
        if (boardId != null && boardRepository.findById(boardId) == null) {
            throw new Exception404("해당 게시판을 찾을 수 없습니다.");
        }

        List<ArticleResponse> articles = articleService.getAllArticles();
        if (boardId != null) {
            List<ArticleResponse> filteredArticles = articles.stream()
                    .filter(article -> boardId.equals(article.getBoardId()))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(filteredArticles, HttpStatus.OK);
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @DeleteMapping("/articles/{id}")
    @ResponseBody
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        if (articleService.getArticleById(id) == null) {
            throw new Exception404("해당 게시물을 찾을 수 없습니다.");
        }
        articleService.deleteArticle(id);
        return new ResponseEntity<>("게시글 삭제 완료!", HttpStatus.OK);
    }
}