package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @PostMapping("/posts")
    @ResponseBody
    public ResponseEntity<String> createPostFromPostman(@RequestBody Article input) {
        if (input.getId() == null) {
            long nextId = articleService.getAllArticles().size();
            input.setId(nextId);
        }

        // 설계도 매핑 요구사항 충족을 위한 가상 DB 자동 연동
        if (input.getBoardId() == null) {
            input.setBoardId(1L);
            if (boardRepository.findById(1L) == null) {
                boardRepository.save(new Board(1L, "자유게시판"));
            }
        }

        if (input.getMemberId() == null) {
            input.setMemberId(input.getId());
            if (memberRepository.findById(input.getId()) == null) {
                memberRepository.save(new Member(input.getId(), "회원" + input.getId(), "user" + input.getId() + "@test.com", "1234"));
            }
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
    public ResponseEntity<List<ArticleResponse>> getArticles() {
        List<ArticleResponse> articles = articleService.getAllArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
}