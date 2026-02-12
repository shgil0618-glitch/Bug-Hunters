package com.thejoa703.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.thejoa703.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 1. 카테고리별 조회 (삭제되지 않은 글 최신순)
    List<Post> findByCategoryAndDeletedFalseOrderByCreatedAtDesc(String category);

    // 2. 제목 검색 기능
    List<Post> findByTitleContainingAndDeletedFalse(String keyword);

    // 3. 해시태그 검색
    List<Post> findByHashtags_NameAndDeletedFalse(String name);

    // 4. 전체 게시글 조회 - Oracle 네이티브 페이징
    @Query(
        value = "SELECT * FROM ( " +
                "    SELECT a.*, ROWNUM rnum FROM ( " +
                "        SELECT * FROM POSTS WHERE DELETED = 0 ORDER BY CREATED_AT DESC " +
                "    ) a WHERE ROWNUM <= :end " +
                ") WHERE rnum >= :start",
        nativeQuery = true
    )  
    List<Post> findPostsWithPaging(@Param("start") int start, @Param("end") int end);

    // 5. 특정 유저가 좋아요한 게시물 페이징
    @Query(
        value = "SELECT * FROM ( " +
                "    SELECT a.*, ROWNUM rnum FROM ( " +
                "        SELECT po.* FROM POSTS po " +
                "        WHERE po.ID IN ( " +
                "            SELECT pl.POST_ID FROM POST_LIKES pl WHERE pl.APP_USER_ID = :userId " +
                "        ) AND po.DELETED = 0 " +
                "        ORDER BY po.CREATED_AT DESC " +
                "    ) a WHERE ROWNUM <= :end " +
                ") WHERE rnum >= :start",
        nativeQuery = true
    ) 
    List<Post> findLikedPostsWithPaging(@Param("userId") Long userId, @Param("start") int start, @Param("end") int end);

    // 6. 내 활동 조회 (내가 작성한 글 + 내가 리트윗한 글 통합)
    // UNION ALL 이후에 최종 정렬을 수행하여 Oracle 구문 오류를 방지합니다.
    @Query(
        value = "SELECT * FROM ( " +
                "    SELECT b.*, ROWNUM rnum FROM ( " +
                "        SELECT * FROM ( " +
                "            SELECT po.* FROM POSTS po WHERE po.APP_USER_ID = :userId AND po.DELETED = 0 " +
                "            UNION ALL " +
                "            SELECT po.* FROM POSTS po WHERE po.ID IN ( " +
                "                SELECT r.ORIGINAL_POST_ID FROM RETWEETS r WHERE r.APP_USER_ID = :userId " +
                "            ) AND po.DELETED = 0 " +
                "        ) a " +
                "        ORDER BY a.CREATED_AT DESC " +
                "    ) b WHERE ROWNUM <= :end " +
                ") WHERE rnum >= :start",
        nativeQuery = true
    )  
    List<Post> findMyPostsAndRetweetsWithPaging(@Param("userId") Long userId, @Param("start") int start, @Param("end") int end);

    // 7. 카테고리별 페이징 조회
    @Query(
        value = "SELECT * FROM ( " +
                "    SELECT a.*, ROWNUM rnum FROM ( " +
                "        SELECT * FROM POSTS WHERE CATEGORY = :category AND DELETED = 0 ORDER BY CREATED_AT DESC " +
                "    ) a WHERE ROWNUM <= :end " +
                ") WHERE rnum >= :start",
        nativeQuery = true
    )
    List<Post> findByCategoryWithPaging(@Param("category") String category, @Param("start") int start, @Param("end") int end);
}