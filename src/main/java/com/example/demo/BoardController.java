package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/boards")
public class BoardController {

    private final BoardRepository boardRepository;
    private final ArticleService articleService;

    public BoardController(BoardRepository boardRepository, ArticleService articleService) {
        this.boardRepository = boardRepository;
        this.articleService = articleService;
    }

    @GetMapping
    @ResponseBody
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Board getBoardById(@PathVariable Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 게시판을 찾을 수 없습니다."));
    }

    @GetMapping("/posts")
    public String getPosts(@RequestParam("boardId") Long boardId, Model model) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시판을 찾을 수 없습니다."));

        model.addAttribute("boardName", board.getName());
        model.addAttribute("articles", articleService.getArticlesByBoardId(boardId));
        return "board_posts";
    }

    @PostMapping
    @ResponseBody
    public String createBoard(@Valid @RequestBody Board board) {
        boardRepository.save(board);
        return "게시판 생성 성공 (ID: " + board.getId() + ")";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public String deleteBoard(@PathVariable Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 게시판을 찾을 수 없습니다."));

        boardRepository.deleteById(id);

        return "게시판 삭제 완료";
    }
}