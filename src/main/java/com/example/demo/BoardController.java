package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardRepository boardRepository;
    private final ArticleService articleService;

    public BoardController(BoardRepository boardRepository, ArticleService articleService) {
        this.boardRepository = boardRepository;
        this.articleService = articleService;
    }

    @GetMapping
    public List<Board> getAllBoards() {
        return new ArrayList<>(boardRepository.getBoardMapValues());
    }

    @GetMapping("/{id}")
    public Board getBoardById(@PathVariable Long id) {
        Board board = boardRepository.findById(id);
        if (board == null) {
            throw new Exception404("해당 게시판을 찾을 수 없습니다.");
        }
        return board;
    }

    @PostMapping
    public String createBoard(@Valid @RequestBody Board board) {
        boardRepository.save(board);
        return "게시판 생성 성공 (ID: " + board.getId() + ")";
    }

    @PutMapping("/{id}")
    public String updateBoard(@PathVariable Long id, @Valid @RequestBody Board updatedBoard) {
        Board board = boardRepository.findById(id);
        if (board == null) {
            throw new Exception404("해당 게시판을 찾을 수 없습니다.");
        }
        board.setName(updatedBoard.getName());
        boardRepository.save(board);
        return "게시판 수정 완료";
    }

    @DeleteMapping("/{id}")
    public String deleteBoard(@PathVariable Long id) {
        Board board = boardRepository.findById(id);
        if (board == null) {
            throw new Exception404("해당 게시판을 찾을 수 없습니다.");
        }

        boolean hasArticles = articleService.getAllArticles().stream()
                .anyMatch(article -> id.equals(article.getBoardId()));
        if (hasArticles) {
            throw new Exception400("게시물이 존재하는 게시판은 삭제할 수 없습니다.");
        }

        return "게시판 삭제 완료";
    }
}