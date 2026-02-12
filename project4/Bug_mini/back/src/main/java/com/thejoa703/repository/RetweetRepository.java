package com.thejoa703.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.thejoa703.entity.Post;
import com.thejoa703.entity.Retweet;

public interface RetweetRepository extends JpaRepository<Retweet, Long> {

    // 1. 특정 유저의 특정 게시글 리트윗 조회 (JPQL)
    @Query("SELECT r FROM Retweet r WHERE r.user.id = :userId AND r.originalPost.id = :postId")
    Optional<Retweet> findByUserAndOriginalPost(@Param("userId") Long userId, @Param("postId") Long postId);

    // 2. 중복 리트윗 방지 (count)
    @Query("SELECT COUNT(r) FROM Retweet r WHERE r.user.id = :userId AND r.originalPost.id = :postId")
    long countByUserAndOriginalPost(@Param("userId") Long userId, @Param("postId") Long postId);

 // RetweetRepository.java
    @Modifying(clearAutomatically = true, flushAutomatically = true) // ✅ 두 옵션 모두 추가
    @Transactional
    @Query("DELETE FROM Retweet r WHERE r.user.id = :userId AND r.originalPost.id = :postId")
    void deleteByUserAndOriginalPost(@Param("userId") Long userId, @Param("postId") Long postId);

    // 4. 특정 게시글의 리트윗 수 집계
    @Query("SELECT COUNT(r) FROM Retweet r WHERE r.originalPost.id = :postId")
    long countByOriginalPostId(@Param("postId") Long postId);
    
    // 5. 특정 유저가 리트윗한 글 ID 목록 조회 (프론트 체크용)
    @Query("SELECT r.originalPost.id FROM Retweet r WHERE r.user.id = :userId")
    List<Long> findOriginalPostIdsByUserId(@Param("userId") Long userId);
    
    // 6. 내가 리트윗한 포스트 목록 (Oracle 11g Paging Native Query)
    @Query(
      value = "SELECT * FROM ( " +
              "  SELECT p.*, ROWNUM AS rnum FROM ( " +
              "    SELECT po.* FROM POSTS po " +
              "    WHERE po.ID IN ( " +
              "        SELECT r.ORIGINAL_POST_ID FROM RETWEETS r WHERE r.APP_USER_ID = :userId " +
              "    ) AND po.deleted = 0 " +
              "    ORDER BY po.created_at DESC " +
              "  ) p WHERE ROWNUM <= :end " +
              ") WHERE rnum >= :start",
      nativeQuery = true
    )
    List<Post> findRetweetedPostsWithPaging(@Param("userId") Long userId,
                                            @Param("start") int start,
                                            @Param("end") int end);
}