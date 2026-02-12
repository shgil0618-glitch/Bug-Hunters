package com.thejoa703.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

import com.thejoa703.dto.request.RetweetRequestDto;
import com.thejoa703.dto.response.RetweetResponseDto;
import com.thejoa703.entity.AppUser;
import com.thejoa703.entity.Post;
import com.thejoa703.entity.Retweet;
import com.thejoa703.repository.AppUserRepository;
import com.thejoa703.repository.PostRepository;
import com.thejoa703.repository.RetweetRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // 로그 기록을 위해 추가

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RetweetService {

    private final RetweetRepository retweetRepository;
    private final AppUserRepository userRepository;
    private final PostRepository postRepository;

    /**
     * 리트윗 추가
     */
    public RetweetResponseDto addRetweet(Long userId, RetweetRequestDto dto) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));
        Post post = postRepository.findById(dto.getOriginalPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // 본인 글 체크
        if (post.getUser().getId().equals(userId)) {
            throw new IllegalStateException("자신의 게시글은 공유(리트윗)할 수 없습니다.");
        }

        // 중복 체크 (조회 시 메서드 파라미터 확인 필요)
        if (retweetRepository.countByUserAndOriginalPost(userId, dto.getOriginalPostId()) > 0) {
            throw new IllegalStateException("이미 공유한 게시글입니다.");
        }

        try {
            Retweet saved = retweetRepository.save(new Retweet(user, post));
            long count = retweetRepository.countByOriginalPostId(post.getId());

            return RetweetResponseDto.builder()
                    .id(saved.getId())
                    .userId(user.getId())
                    .originalPostId(post.getId())
                    .createdAt(saved.getCreatedAt())
                    .retweetCount(count)
                    .build();
        } catch (Exception e) {
            log.error("리트윗 저장 중 서버 오류: ", e);
            throw new RuntimeException("데이터베이스 저장 중 오류가 발생했습니다.");
        }
    }

    /**
     * 리트윗 취소 (삭제)
     */
 // RetweetService.java 내 removeRetweet 메서드 부분 수정
    public RetweetResponseDto removeRetweet(Long userId, Long postId) {
        // 1. 엔티티를 찾습니다.
        Optional<Retweet> retweetOpt = retweetRepository.findByUserAndOriginalPost(userId, postId);

        // 2. 만약 없다면? 에러를 던지지 말고 그냥 현재 카운트만 담아서 돌려줍니다. (방어적 설계)
        if (retweetOpt.isEmpty()) {
            long currentCount = retweetRepository.countByOriginalPostId(postId);
            return RetweetResponseDto.builder()
                    .userId(userId)
                    .originalPostId(postId)
                    .retweetCount(currentCount)
                    .build();
        }

        // 3. 데이터가 있으면 삭제
        retweetRepository.delete(retweetOpt.get());
        retweetRepository.flush(); // 즉시 반영

        // 4. 삭제 후 최신 카운트 계산
        long count = retweetRepository.countByOriginalPostId(postId);

        return RetweetResponseDto.builder()
                .userId(userId)
                .originalPostId(postId)
                .retweetCount(count)
                .build();
    }

    @Transactional(readOnly = true)
    public boolean hasRetweeted(Long userId, Long postId) {
        return retweetRepository.countByUserAndOriginalPost(userId, postId) > 0;
    }

    @Transactional(readOnly = true)
    public long countRetweets(Long postId) {
        return retweetRepository.countByOriginalPostId(postId);
    }

    @Transactional(readOnly = true)
    public List<Long> findMyRetweets(Long userId) {
        return retweetRepository.findOriginalPostIdsByUserId(userId);
    }
}