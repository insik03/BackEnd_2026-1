package com.example.demo;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id")
    @JsonProperty("author_id")
    private Long authorId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
    private String title;
    private String content;

    @Column(name = "created_date")
    private String date;

    @Column(name = "modified_date")
    private String updateDate;

    public Article() {}

    public Article(Long id, Long authorId, Board board, String title, String content, String date, String updateDate) {
        this.id = id;
        this.authorId = authorId;
        this.board = board;
        this.title = title;
        this.content = content;
        this.date = date;
        this.updateDate = updateDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public Board getBoard() { return board; }
    public void setBoard(Board board) { this.board = board; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getUpdateDate() { return updateDate; }
    public void setUpdateDate(String updateDate) { this.updateDate = updateDate; }
}