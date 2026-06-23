package com.example.demo;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ArticleResponse {
    private Long id;

    @JsonProperty("author_id")
    private Long authorId;

    @JsonProperty("board_id")
    private Long boardId;
    private String title;
    private String content;

    @JsonProperty("created_date")
    private String createdDate;

    @JsonProperty("modified_date")
    private String modifiedDate;

    public ArticleResponse(Long id, Long authorId, Long boardId, String title, String content, String createdDate, String modifiedDate) {
        this.id = id;
        this.authorId = authorId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Long getId() { return id; }
    public Long getAuthorId() { return authorId; }
    public Long getBoardId() { return boardId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCreatedDate() { return createdDate; }
    public String getModifiedDate() { return modifiedDate; }
}