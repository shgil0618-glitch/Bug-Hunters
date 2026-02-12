package com.thejoa703.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "POSTS")
@Getter @Setter 
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "post_seq")
    @SequenceGenerator(name = "post_seq", sequenceName = "POST_SEQ" , allocationSize = 1) 
    private Long id;

    // 1. 기본 정보 (제목, 카테고리, 인원, 난이도)
    @Column(nullable = false, length = 200)
    private String title;          // 게시글 제목

    @Column(nullable = false)
    private String category;       // 카테고리 (전체, 한식, 중식, 기타 등)

    @Column(nullable = false)
    private Integer servingSize;   // 인원수

    @Column(nullable = false)
    private String difficulty;     // 난이도

    // 2. 텍스트 정보 (요약, 재료, 단계별 설명)
    @Column(length = 1000)
    private String description;    // 간단 설명

    @Lob
    @Column(nullable = false)
    private String ingredients;    // 재료 (프론트에서 배열을 JSON.stringify해서 넘겨줄 것)

    @Lob
    @Column(name = "CONTENT", nullable = false) // 실제 DB의 CONTENT와 연결
    private String instructions;   // 상세 설명 단계 (긴 텍스트)

    // 3. 메타 정보 (날짜, 삭제여부)
    @Column(nullable = false, name="CREATED_AT")
    private LocalDateTime createdAt;

    @Column(nullable = false, name="UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column
    private boolean deleted = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="APP_USER_ID", nullable=false)
    private AppUser user; // 작성자

    // --- 관계 매핑 ---

    // 이미지 리스트 (0번 인덱스가 대표이미지, 1번부터 설명 이미지)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    
    @OneToMany(mappedBy = "originalPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Retweet> retweets = new ArrayList<>();
    
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> likes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name="POST_HASHTAG",
            joinColumns = @JoinColumn(name="POST_ID"), 
            inverseJoinColumns = @JoinColumn(name="HASHTAG_ID") 
    )
    private List<Hashtag> hashtags = new ArrayList<>();

    // --- 편의 기능 ---

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    void onUpdate() { 
        this.updatedAt = LocalDateTime.now();
    }

    // 대표 이미지(0번)만 쏙 뽑아오는 로직 (프론트에서 쓰기 편함)
    public String getMainImage() {
        if (images != null && !images.isEmpty()) {
            return images.get(0).getSrc();
        }
        return null; // 이미지가 없을 경우
    }
    
 // --- 비즈니스 로직 (추가 필수) ---

    /**
     * 좋아요 수 계산
     */
    public int getLikeCount() {
        return likes != null ? likes.size() : 0;
    }

    /**
     * 댓글 수 계산
     */
    public int getCommentCount() {
        return comments != null ? comments.size() : 0;
    }
}