package com.example.demo;

public class ArticleResponse {
    private Long boardId;
    private String title;
    private String author;
    private String date;
    private String content;

    public ArticleResponse(Long boardId, String title, String author, String date, String content) {
        this.boardId = boardId;
        this.title = title;
        this.author = author;
        this.date = date;
        this.content = content;
    }

    public Long getBoardId() { return boardId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getDate() { return date; }
    public String getContent() { return content; }
}