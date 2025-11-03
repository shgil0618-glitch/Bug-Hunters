package com.thejoa703.dto;

import java.time.LocalDateTime;

public class ComunityDto {
    private int postId;            // 게시글 ID
    private String id;             // 작성자
    private String title;          // 제목
    private String content;        // 본문
    private int categoryId;        // 카테고리 번호
    private int views;             // 조회수
    private LocalDateTime createdAt;   // 작성일
    private LocalDateTime updatedAt;   // 수정일

    // 기본 생성자
    public ComunityDto() {}

    // 전체 생성자
    public ComunityDto(int postId, String id, String title, String content, int categoryId, int views, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.id = id;
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.views = views;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // getters & setters
    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "ComunityDto [postId=" + postId + ", id=" + id + ", title=" + title +
                ", content=" + content + ", categoryId=" + categoryId + ", views=" + views +
                ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }
}
