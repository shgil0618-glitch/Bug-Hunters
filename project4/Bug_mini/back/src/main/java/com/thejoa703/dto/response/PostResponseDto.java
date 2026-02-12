package com.thejoa703.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.thejoa703.entity.Hashtag;
import com.thejoa703.entity.Image;
import com.thejoa703.entity.Post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDto {
    private Long id;
    private String title;          // 추가
    private String category;       // 추가
    private Integer servingSize;   // 추가
    private String difficulty;     // 추가
    private String description;    // 추가
    private String ingredients;    // 추가
    private String instructions;   // 추가
    private String content;
    
    private String authorNickname;
    private String authorUfile; 
    private Long authorId;
    
    private String mainImage;      // ✅ 추가: images[0]을 추출한 대표 이미지
    private List<String> imageUrls;
    private List<String> hashtags;
    
    private int likeCount;
    private int commentCount;
    private long retweetCount;
    private LocalDateTime createdAt;
    private boolean deleted;

    public static PostResponseDto from(Post post) {
        PostResponseDto dto = new PostResponseDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setCategory(post.getCategory());
        dto.setServingSize(post.getServingSize());
        dto.setDifficulty(post.getDifficulty());
        dto.setDescription(post.getDescription());
        dto.setIngredients(post.getIngredients());
        dto.setInstructions(post.getInstructions());

        if (post.getUser() != null) {
            dto.setAuthorNickname(post.getUser().getNickname());
            dto.setAuthorId(post.getUser().getId());
            dto.setAuthorUfile(post.getUser().getUfile()); // ✅ 프로필 이미지 매핑

        }

        // 이미지 처리
        List<String> urls = post.getImages().stream()
                .map(Image::getSrc)
                .collect(Collectors.toList());
        dto.setImageUrls(urls);
        
        // ✅ 0번 이미지를 대표 이미지로 설정
        if (!urls.isEmpty()) {
            dto.setMainImage(urls.get(0));
        }

        dto.setHashtags(
            post.getHashtags().stream()
                .map(Hashtag::getName)
                .collect(Collectors.toList())
        );

        dto.setLikeCount(post.getLikeCount());
        dto.setCommentCount(post.getCommentCount());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setDeleted(post.isDeleted());
        
        // RetweetCount는 필요 시 post.getRetweets().size() 등으로 세팅 가능
        if(post.getRetweets() != null) {
            dto.setRetweetCount(post.getRetweets().size());
        }
//     // 댓글 목록 매핑 (Comment 엔티티를 CommentResponseDto로 변환)
//        if (post.getComments() != null) {
//            dto.setComments(post.getComments().stream()
//                    .filter(c -> !c.isDeleted()) // 삭제되지 않은 댓글만
//                    .map(CommentResponseDto::fromEntity) // Comment용 DTO 변환 메서드 호출
//                    .collect(Collectors.toList()));
//        }
        

        return dto;
    }
}