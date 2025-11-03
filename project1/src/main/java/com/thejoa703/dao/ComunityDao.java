package com.thejoa703.dao;

import com.thejoa703.dto.ComunityDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComunityDao {

    private final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String user = "SCOTT";
    private final String password = "TIGER";

    // 조회수 증가
    public void incrementViews(int postId) {
        String sql = "UPDATE COMMUNITY_TB SET views = views + 1 WHERE postId=?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 단일 게시글 조회
    public ComunityDto getPost(int postId) {
        ComunityDto post = null;
        String sql = "SELECT * FROM COMMUNITY_TB WHERE postId = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                post = mapRowToDto(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    // 전체 게시글 조회
    public List<ComunityDto> getAllPosts() {
        List<ComunityDto> list = new ArrayList<>();
        String sql = "SELECT * FROM COMMUNITY_TB ORDER BY createdAt DESC";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapRowToDto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 게시글 등록 (postId는 시퀀스로 처리)
    public void insertPost(ComunityDto post) {
        String sql = "INSERT INTO COMMUNITY_TB(postId, id, title, content, categoryId, views, createdAt) "
                   + "VALUES(COMMUNITY_SEQ.NEXTVAL, ?, ?, ?, ?, 0, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, post.getId());
            pstmt.setString(2, post.getTitle());
            pstmt.setString(3, post.getContent());
            pstmt.setInt(4, post.getCategoryId());
            pstmt.setTimestamp(5, Timestamp.valueOf(post.getCreatedAt()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 게시글 수정
    public void updatePost(ComunityDto post) {
        String sql = "UPDATE COMMUNITY_TB SET title=?, content=?, categoryId=?, updatedAt=? WHERE postId=?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getContent());
            pstmt.setInt(3, post.getCategoryId());
            pstmt.setTimestamp(4, Timestamp.valueOf(post.getUpdatedAt()));
            pstmt.setInt(5, post.getPostId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 게시글 삭제
    public void deletePost(int postId) {
        String sql = "DELETE FROM COMMUNITY_TB WHERE postId=?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ResultSet -> DTO 매핑
    private ComunityDto mapRowToDto(ResultSet rs) throws SQLException {
        return new ComunityDto(
                rs.getInt("postId"),
                rs.getString("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getInt("categoryId"),
                rs.getInt("views"),
                rs.getTimestamp("createdAt").toLocalDateTime(),
                rs.getTimestamp("updatedAt") != null ? rs.getTimestamp("updatedAt").toLocalDateTime() : null
        );
    }
}
