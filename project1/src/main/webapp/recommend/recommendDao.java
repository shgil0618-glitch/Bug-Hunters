package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dto.RecommendDTO;

public class RecommendDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    // DB 연결 메소드
    public void getConn() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 오라클 접속정보
            String user = "system"; // 사용자명 (수업용 계정 or 본인 계정으로 변경)
            String password = "1234"; // 비밀번호
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ DB 연결 성공");
        } catch (Exception e) {
            System.out.println("❌ DB 연결 실패: " + e.getMessage());
        }
    }

    //  DB 연결 해제
    public void getClose() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  추천 등록 (INSERT)
    public int insertRecommend(RecommendDTO dto) {
        int result = 0;
        try {
            getConn();
            String sql = "INSERT INTO RECOMMEND_TB (recId, id, foodId, type, feedback, createdAt) " +
                         "VALUES (rec_seq.NEXTVAL, ?, ?, ?, ?, SYSDATE)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dto.getUserId());
            ps.setInt(2, dto.getFoodId());
            ps.setString(3, dto.getType());
            ps.setString(4, dto.getFeedback());
            result = ps.executeUpdate();
            System.out.println("✅ 추천 등록 성공");
        } catch (Exception e) {
            System.out.println("❌ 추천 등록 실패: " + e.getMessage());
        } finally {
            getClose();
        }
        return result;
    }

    //  전체 추천 조회 (SELECT *)
    public List<RecommendDTO> getAllRecommend() {
        List<RecommendDTO> list = new ArrayList<>();
        try {
            getConn();
            String sql = "SELECT * FROM RECOMMEND_TB ORDER BY recId DESC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                RecommendDTO dto = new RecommendDTO();
                dto.setRecId(rs.getInt("recId"));
                dto.setUserId(rs.getString("id"));
                dto.setFoodId(rs.getInt("foodId"));
                dto.setType(rs.getString("type"));
                dto.setFeedback(rs.getString("feedback"));
                dto.setCreatedAt(rs.getDate("createdAt"));
                list.add(dto);
            }
        } catch (Exception e) {
            System.out.println("❌ 전체 추천 조회 실패: " + e.getMessage());
        } finally {
            getClose();
        }
        return list;
    }

    //  특정 유저 추천 조회
    public List<RecommendDTO> getRecommendByUser(String userId) {
        List<RecommendDTO> list = new ArrayList<>();
        try {
            getConn();
            String sql = "SELECT * FROM RECOMMEND_TB WHERE id = ? ORDER BY recId DESC";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                RecommendDTO dto = new RecommendDTO();
                dto.setRecId(rs.getInt("recId"));
                dto.setUserId(rs.getString("id"));
                dto.setFoodId(rs.getInt("foodId"));
                dto.setType(rs.getString("type"));
                dto.setFeedback(rs.getString("feedback"));
                dto.setCreatedAt(rs.getDate("createdAt"));
                list.add(dto);
            }
        } catch (Exception e) {
            System.out.println("❌ 유저별 추천 조회 실패: " + e.getMessage());
        } finally {
            getClose();
        }
        return list;
    }

    //  추천 삭제
    public int deleteRecommend(int recId) {
        int result = 0;
        try {
            getConn();
            String sql = "DELETE FROM RECOMMEND_TB WHERE recId = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, recId);
            result = ps.executeUpdate();
            System.out.println("✅ 추천 삭제 성공");
        } catch (Exception e) {
            System.out.println("❌ 추천 삭제 실패: " + e.getMessage());
        } finally {
            getClose();
        }
        return result;
    }
}
