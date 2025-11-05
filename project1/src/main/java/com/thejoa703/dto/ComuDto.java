package com.thejoa703.dto;

import java.time.LocalDateTime;

public class ComuDto {
	private int postId;
	private int id;
	private String title;
	private String content;
	private int categoryId;
	private int   views;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;  
	
	public ComuDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ComuDto(int postId, int id, String title, String content, int categoryId, int views, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		super();
		this.postId = postId;
		this.id = id;
		this.title = title;
		this.content = content;
		this.categoryId = categoryId;
		this.views = views;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public ComuDto(int postId, int id, String title, String content, int categoryId, int views,
			LocalDateTime createdAt) {
		super();
		this.postId = postId;
		this.id = id;
		this.title = title;
		this.content = content;
		this.categoryId = categoryId;
		this.views = views;
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "ComuDto [postId=" + postId + ", id=" + id + ", title=" + title + ", content=" + content
				+ ", categoryId=" + categoryId + ", views=" + views + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	
}

/*
CREATE TABLE COMMUNITY_TB (
    postId      NUMBER(8) PRIMARY KEY,      -- 게시글 ID
    id      VARCHAR2(30) NOT NULL,      -- 작성자
    title        VARCHAR2(200) NOT NULL,    -- 제목
    content      CLOB NOT NULL,             -- 본문
    categoryId  NUMBER(3) NOT NULL,           -- 카테고리 번호
    views        NUMBER(6) DEFAULT 0,       -- 조회수
    createdAt   DATE DEFAULT SYSDATE,       -- 작성일
    updatedAt   DATE,                       -- 수정일
    FOREIGN KEY (id) REFERENCES users(id),
    FOREIGN KEY (categoryId) REFERENCES CATEGORY_TB(categoryId)
);
*/
