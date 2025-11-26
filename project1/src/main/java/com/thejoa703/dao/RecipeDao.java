package com.thejoa703.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.thejoa703.dto.RecipeDto;

public class RecipeDao {

    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:xe";
    String user = "scott";
    String pass = "tiger";

    public RecipeDao() {
        try {
            Class.forName(driver);
        } catch (Exception e) { e.printStackTrace(); }
    }

    // --------------------- INSERT -------------------------
    public int insert(RecipeDto dto) {
        int result = -1;
        String sql = "INSERT INTO recipes(recipe_id, title, category, description, cook_time, difficulty, servings, rfile, created_at) "
                   + "VALUES (recipe_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, sysdate)";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection(url, user, pass);
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dto.getTitle() != null ? dto.getTitle() : "");
            pstmt.setString(2, dto.getCategory() != null ? dto.getCategory() : "");
            pstmt.setString(3, dto.getDescription() != null ? dto.getDescription() : "");
            pstmt.setInt(4, dto.getCookTime());
            pstmt.setString(5, dto.getDifficulty() != null ? dto.getDifficulty() : "");
            pstmt.setInt(6, dto.getServings());
            pstmt.setString(7, dto.getRfile() != null ? dto.getRfile() : "");

            if (pstmt.executeUpdate() > 0) result = 1;

        } catch (Exception e) { e.printStackTrace(); }
        finally {
            if(pstmt != null) try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
            if(conn != null) try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }

        return result;
    }

    // --------------------- LIST -------------------------
    public List<RecipeDto> list() {
        List<RecipeDto> result = new ArrayList<>();
        String sql = "SELECT * FROM recipes ORDER BY recipe_id DESC";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, user, pass);
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                RecipeDto dto = new RecipeDto();
                dto.setRecipeId(rs.getInt("recipe_id"));
                dto.setTitle(rs.getString("title"));
                dto.setCategory(rs.getString("category"));
                dto.setDescription(rs.getString("description"));
                dto.setCookTime(rs.getInt("cook_time"));
                dto.setDifficulty(rs.getString("difficulty"));
                dto.setServings(rs.getInt("servings"));
                dto.setRfile(rs.getString("rfile"));
                dto.setCreatedAt(rs.getTimestamp("created_at"));
                result.add(dto);
            }

        } catch (Exception e) { e.printStackTrace(); }
        finally {
            if(rs != null) try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            if(pstmt != null) try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
            if(conn != null) try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }

        return result;
    }

    // --------------------- DETAIL -------------------------
    public RecipeDto detail(int recipeId) {
        RecipeDto result = null;
        String sql = "SELECT * FROM recipes WHERE recipe_id=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, user, pass);
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, recipeId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = new RecipeDto();
                result.setRecipeId(rs.getInt("recipe_id"));
                result.setTitle(rs.getString("title"));
                result.setCategory(rs.getString("category"));
                result.setDescription(rs.getString("description"));
                result.setCookTime(rs.getInt("cook_time"));
                result.setDifficulty(rs.getString("difficulty"));
                result.setServings(rs.getInt("servings"));
                result.setRfile(rs.getString("rfile"));
                result.setCreatedAt(rs.getTimestamp("created_at"));
            }

        } catch (Exception e) { e.printStackTrace(); }
        finally {
            if(rs != null) try { rs.close(); } catch (Exception e) { e.printStackTrace(); }
            if(pstmt != null) try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
            if(conn != null) try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }

        return result;
    }

    // --------------------- UPDATE -------------------------
    public int update(RecipeDto dto) {
        int result = -1;
        String sql = "UPDATE recipes SET title=?, category=?, description=?, cook_time=?, difficulty=?, servings=?, rfile=? WHERE recipe_id=?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection(url, user, pass);
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getCategory());
            pstmt.setString(3, dto.getDescription());
            pstmt.setInt(4, dto.getCookTime());
            pstmt.setString(5, dto.getDifficulty());
            pstmt.setInt(6, dto.getServings());
            pstmt.setString(7, dto.getRfile());
            pstmt.setInt(8, dto.getRecipeId());

            if (pstmt.executeUpdate() > 0) result = 1;

        } catch (Exception e) { e.printStackTrace(); }
        finally {
            if(pstmt != null) try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
            if(conn != null) try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }

        return result;
    }

    // --------------------- DELETE -------------------------
    public int delete(int recipeId) {
        int result = -1;
        String sql = "DELETE FROM recipes WHERE recipe_id=?";

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection(url, user, pass);
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, recipeId);

            if (pstmt.executeUpdate() > 0) result = 1;

        } catch (Exception e) { e.printStackTrace(); }
        finally {
            if(pstmt != null) try { pstmt.close(); } catch (Exception e) { e.printStackTrace(); }
            if(conn != null) try { conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }

        return result;
    }
}
