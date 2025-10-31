package com.thejoa703.dto;

import java.time.LocalDateTime;

public class PostDto {
	////// ##1. 필드명맞추기
	private int 	 id;
	private int 	 appUserId;
	private String   title;
	private String   content;
	private String   pass;
	private LocalDateTime   createdAt;
	private int hit;
	private String   email; 
	// 생성자 / toString 
	// ##2. getters+setters 
	public PostDto() { super(); }
	///////////////////////
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	///////////////////////
	public PostDto(int id, int appUserId, String title, String content, String pass, LocalDateTime createdAt, int hit) { super(); this.id = id; this.appUserId = appUserId; this.title = title; this.content = content; this.pass = pass; this.createdAt = createdAt; this.hit = hit; }
	public PostDto(int id, int appUserId, String title, String content, String pass, LocalDateTime createdAt, int hit,
			String email) {
		super();
		this.id = id;
		this.appUserId = appUserId;
		this.title = title;
		this.content = content;
		this.pass = pass;
		this.createdAt = createdAt;
		this.hit = hit;
		this.email = email;
	}
	@Override public String toString() { return "PostDto [id=" + id + ", appUserId=" + appUserId + ", title=" + title + ", content=" + content + ", pass=" + pass + ", createdAt=" + createdAt + ", hit=" + hit + "]"; }
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public int getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(int appUserId) {
		this.appUserId = appUserId;
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
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	
}

/*
		3. dto
			[com.thejoa703.dto] - PostDto
 

			| 컬럼명        | 데이터 타입       | 제약 조건       | 설명 |
			|---------------|-------------------|------------------|------|
			| `id`          | `NUMBER`          | `PRIMARY KEY`    | 게시글 고유 ID |
			| `app_user_id` | `NUMBER`          | `NOT NULL`       | 작성자 ID (`appuser` 테이블 참조) |
			| `title`       | `VARCHAR2(200)`   | `NOT NULL`       | 게시글 제목 |
			| `content`     | `CLOB`            | `NOT NULL`       | 게시글 내용 (대용량 텍스트, 최대 4GB) |
			| `pass`        | `VARCHAR2(100)`   | —                | 비회원 삭제용 비밀번호 |
			| `created_at`  | `DATE`            | `DEFAULT SYSDATE`| 작성일 |
			| `hit`         | `NUMBER`          | `DEFAULT 0`      | 조회수 |

*/