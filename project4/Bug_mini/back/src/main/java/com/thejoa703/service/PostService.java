package com.thejoa703.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.thejoa703.dto.request.PostRequestDto;
import com.thejoa703.dto.response.PostResponseDto;
import com.thejoa703.entity.AppUser;
import com.thejoa703.entity.Hashtag;
import com.thejoa703.entity.Image;
import com.thejoa703.entity.Post;
import com.thejoa703.repository.AppUserRepository;
import com.thejoa703.repository.HashtagRepository;
import com.thejoa703.repository.PostRepository;
import com.thejoa703.repository.RetweetRepository;
import com.thejoa703.util.FileStorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final AppUserRepository userRepository;
    private final HashtagRepository hashtagRepository;
    private final FileStorageService fileStorageService;
    private final RetweetRepository retweetRepository;

    /**
     * 게시글 작성 (레시피 등록)
     */
    public PostResponseDto createPost(Long userId, PostRequestDto dto, List<MultipartFile> files) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setCategory(dto.getCategory());
        post.setServingSize(dto.getServingSize());
        post.setDifficulty(dto.getDifficulty());
        post.setDescription(dto.getDescription());
        post.setIngredients(dto.getIngredients());

        // Instructions 필드 우선, 없으면 Content 사용 (Oracle CONTENT 컬럼 매핑)
        String instructionsValue = (dto.getInstructions() != null && !dto.getInstructions().isBlank()) 
                                   ? dto.getInstructions() 
                                   : dto.getContent();
        
        if (instructionsValue == null || instructionsValue.isBlank()) {
            instructionsValue = dto.getDescription(); 
        }
        
        post.setInstructions(instructionsValue);
        post.setUser(user);

        // 이미지 처리
        if (files != null && !files.isEmpty()) {
            files.forEach(file -> {
                String url = fileStorageService.upload(file);
                Image image = new Image();
                image.setSrc(url);
                image.setPost(post);
                post.getImages().add(image);
            });
        }

        // 해시태그 처리
        processHashtags(post, dto.getHashtags());

        return convertToResponseDto(postRepository.save(post));
    }

    /**
     * 단일 게시글 조회
     */
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 삭제된 게시글입니다."));
        return convertToResponseDto(post);
    }

    /**
     * 전체 게시글 페이징 조회 (Oracle Native)
     */
    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPostsPaged(int page, int size) {
        int start = (page - 1) * size + 1;
        int end = page * size;
        return postRepository.findPostsWithPaging(start, end).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * 특정 유저가 좋아요한 게시글 페이징 조회
     */
    @Transactional(readOnly = true)
    public List<PostResponseDto> getLikedPostsPaged(Long userId, int page, int size) {
        int start = (page - 1) * size + 1;
        int end = page * size;
        return postRepository.findLikedPostsWithPaging(userId, start, end).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * 내 활동 (내 글 + 리트윗) 페이징 조회
     */
    @Transactional(readOnly = true)
    public List<PostResponseDto> getMyPostsAndRetweetsPaged(Long userId, int page, int size) {
        int start = (page - 1) * size + 1;
        int end = page * size;
        log.info("내 활동 조회 요청 - User: {}, Start: {}, End: {}", userId, start, end);
        
        return postRepository.findMyPostsAndRetweetsWithPaging(userId, start, end).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * 해시태그로 검색
     */
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostsByHashtag(String hashtag) {
        String normalized = hashtag.startsWith("#") ? hashtag.substring(1) : hashtag;
        return postRepository.findByHashtags_NameAndDeletedFalse(normalized).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * 카테고리별 검색 (페이징 미적용 버전 - 필요시 레포지토리 페이징 메서드 호출로 변경 가능)
     */
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostsByCategory(String category) {
        if ("전체".equals(category)) {
            return getAllPostsPaged(1, 100); // 전체일 경우 상위 100개 반환
        }
        return postRepository.findByCategoryAndDeletedFalseOrderByCreatedAtDesc(category)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * 게시글 수정
     */
    public PostResponseDto updatePost(Long userId, Long postId, PostRequestDto dto, List<MultipartFile> files) {
        Post post = postRepository.findById(postId)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!post.getUser().getId().equals(userId)) {
            throw new SecurityException("수정 권한이 없습니다.");
        }

        post.setTitle(dto.getTitle());
        post.setCategory(dto.getCategory());
        post.setServingSize(dto.getServingSize());
        post.setDifficulty(dto.getDifficulty());
        post.setDescription(dto.getDescription());
        post.setIngredients(dto.getIngredients());
        
        String instructionsValue = (dto.getInstructions() != null && !dto.getInstructions().isBlank()) 
                                   ? dto.getInstructions() 
                                   : dto.getContent();
        post.setInstructions(instructionsValue);

        // 이미지 수정 (기존 이미지 삭제 후 재등록 - 요구사항에 따라 조절)
        if (files != null && !files.isEmpty()) {
            post.getImages().clear();
            files.forEach(file -> {
                String url = fileStorageService.upload(file);
                Image image = new Image();
                image.setSrc(url);
                image.setPost(post);
                post.getImages().add(image);
            });
        }

        post.getHashtags().clear();
        processHashtags(post, dto.getHashtags());

        return convertToResponseDto(postRepository.save(post));
    }

    /**
     * 게시글 삭제 (Soft Delete)
     */
    public void deletePost(Long userId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        if (!post.getUser().getId().equals(userId)) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }
        post.setDeleted(true);
        postRepository.save(post);
    }

    // --- Private Methods ---

    private void processHashtags(Post post, String hashtagStr) {
        if (hashtagStr != null && !hashtagStr.isEmpty()) {
            Set<String> distinctTags = Arrays.stream(hashtagStr.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toSet());

            distinctTags.forEach(tagStr -> {
                String normalized = tagStr.startsWith("#") ? tagStr.substring(1) : tagStr;
                Hashtag tag = hashtagRepository.findByName(normalized)
                        .orElseGet(() -> {
                            Hashtag newTag = new Hashtag();
                            newTag.setName(normalized);
                            return hashtagRepository.save(newTag);
                        });
                post.getHashtags().add(tag);
            });
        }
    }

    private PostResponseDto convertToResponseDto(Post post) {
        // 엔티티 -> DTO 변환 시 리트윗 수와 이미지 정보를 포함
        PostResponseDto dto = PostResponseDto.from(post);
        dto.setRetweetCount(retweetRepository.countByOriginalPostId(post.getId()));
        return dto;
    }
}