package com.thejoa703.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.thejoa703.dto.request.PostRequestDto;
import com.thejoa703.dto.response.PostResponseDto;
import com.thejoa703.service.AuthUserJwtService;
import com.thejoa703.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Post", description = "레시피 및 게시글 API")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final AuthUserJwtService authUserJwtService;

    // --- [조회 API - 공개] ---

    @Operation(summary = "전체 게시글 페이징 조회 (공개)")
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPostsPaged(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(postService.getAllPostsPaged(page, size));
    }

    @Operation(summary = "게시글 단건 상세 조회 (공개)")
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable(name = "postId") Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @Operation(summary = "해시태그로 게시글 검색 (공개)")
    @GetMapping("/search/hashtag")
    public ResponseEntity<List<PostResponseDto>> searchByHashtag(@RequestParam(name = "tag") String tag) {
        return ResponseEntity.ok(postService.getPostsByHashtag(tag));
    }
    
    @Operation(summary = "카테고리별 게시글 검색 (공개)")
    @GetMapping("/search/category")
    public ResponseEntity<List<PostResponseDto>> searchByCategory(
            @RequestParam("category") String category
    ) {
        return ResponseEntity.ok(postService.getPostsByCategory(category)); 
    }

    // --- [인증 필요 API - JWT] ---

    @Operation(summary = "게시글(레시피) 작성 (JWT 인증 필요)")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponseDto> createPost(
            Authentication authentication,
            @ModelAttribute PostRequestDto dto, 
            @Parameter(description = "업로드할 이미지 파일")
            @RequestPart(name = "files", required = false) List<MultipartFile> files
    ) {
        Long userId = authUserJwtService.getCurrentUserId(authentication);
        return ResponseEntity.ok(postService.createPost(userId, dto, files));
    }

    @Operation(summary = "게시글 수정 (JWT 인증 필요)")
    @PutMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponseDto> updatePost(
            Authentication authentication,
            @PathVariable(name = "postId") Long postId,
            @ModelAttribute PostRequestDto dto,
            @RequestPart(name = "files", required = false) List<MultipartFile> files
    ) {
        Long userId = authUserJwtService.getCurrentUserId(authentication);
        return ResponseEntity.ok(postService.updatePost(userId, postId, dto, files));
    }

    @Operation(summary = "게시글 삭제 (JWT 인증 필요)")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            Authentication authentication,
            @PathVariable(name = "postId") Long postId
    ) {
        Long userId = authUserJwtService.getCurrentUserId(authentication);
        postService.deletePost(userId, postId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "좋아요한 게시글 페이징 조회 (JWT 인증 필요)")
    @GetMapping("/liked")
    public ResponseEntity<List<PostResponseDto>> getLikedPostsPaged(
            Authentication authentication,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Long userId = authUserJwtService.getCurrentUserId(authentication);
        // Pageable 대신 int page, size를 직접 넘겨 서비스에서 start, end 계산 유도
        return ResponseEntity.ok(postService.getLikedPostsPaged(userId, page, size));
    }

    @Operation(summary = "내 작성글 + 리트윗 통합 조회 (JWT 인증 필요)")
    @GetMapping("/my-activity")
    public ResponseEntity<List<PostResponseDto>> getMyPostsAndRetweetsPaged(
            Authentication authentication,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long userId = authUserJwtService.getCurrentUserId(authentication);
        // 서비스 메서드 명칭을 기존 postService 인터페이스/클래스에 있는 이름으로 통일
        return ResponseEntity.ok(postService.getMyPostsAndRetweetsPaged(userId, page, size));
    }
}