package com.example.demo;

public class Article {
    private Long id;
    private Long memberId;
    private Long boardId;
    private String title;
    private String content;
    private String date;
    private String updateDate;

    public Article() {}

    public Article(Long id, Long memberId, Long boardId, String title, String content, String date, String updateDate) {
        this.id = id;
        this.memberId = memberId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.date = date;
        this.updateDate = updateDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }

    public Long getBoardId() { return boardId; }
    public void setBoardId(Long boardId) { this.boardId = boardId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getUpdateDate() { return updateDate; }
    public void setUpdateDate(String updateDate) { this.updateDate = updateDate; }
}