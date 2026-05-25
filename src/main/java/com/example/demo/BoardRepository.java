package com.example.demo;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BoardRepository {
    private final Map<Long, Board> boardMap = new HashMap<>();

    public void save(Board board) {
        boardMap.put(board.getId(), board);
    }

    public Board findById(Long id) {
        return boardMap.get(id);
    }
}