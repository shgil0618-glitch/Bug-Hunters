package com.thejoa703.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.thejoa703.dto.IngredientDto;

public class IngredientDao {

    String driver = "oracle.jdbc.driver.OracleDriver";
    String url = "jdbc:oracle:thin:@localhost:1521:xe";
    String user = "scott";
    String pass = "tiger";

    public IngredientDao() {
        try { Class.forName(driver); } catch (Exception e) { e.printStackTrace(); }
    }

    // INSERT
    public int insert(IngredientDto dto) {
        int result = -1;

        String sql = "INSERT INTO ingredients("
                   + "ingredient_id, recipe_id, ingredient_name, quantity, unit, kcal, carbs, protein, fat, allergens"
                   + ") VALUES (ingredients_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, dto.getRecipeId());
            pstmt.setString(2, dto.getIngredientName());
            pstmt.setDouble(3, dto.getQuantity());
            pstmt.setString(4, dto.getUnit());
            pstmt.setDouble(5, dto.getKcal());
            pstmt.setDouble(6, dto.getCarbs());
            pstmt.setDouble(7, dto.getProtein());
            pstmt.setDouble(8, dto.getFat());
            pstmt.setString(9, dto.getAllergens());

            int presult = pstmt.executeUpdate();
            if (presult > 0) result = 1;

        } catch (Exception e) { e.printStackTrace(); }

        return result;
    }

    // LIST (특정 레시피의 재료)
    public List<IngredientDto> list(int recipeId) {
        List<IngredientDto> list = new ArrayList<>();

        String sql = "SELECT * FROM ingredients WHERE recipe_id=? ORDER BY ingredient_id";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, recipeId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    IngredientDto dto = new IngredientDto();
                    dto.setIngredientId(rs.getInt("ingredient_id"));
                    dto.setRecipeId(rs.getInt("recipe_id"));
                    dto.setIngredientName(rs.getString("ingredient_name"));
                    dto.setQuantity(rs.getInt("quantity"));
                    dto.setUnit(rs.getString("unit"));
                    dto.setKcal(rs.getInt("kcal"));
                    dto.setCarbs(rs.getInt("carbs"));
                    dto.setProtein(rs.getInt("protein"));
                    dto.setFat(rs.getInt("fat"));
                    dto.setAllergens(rs.getString("allergens"));
                    list.add(dto);
                }
            }

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    // UPDATE
    public int update(IngredientDto dto) {
        int result = -1;

        String sql = "UPDATE ingredients "
                   + "SET ingredient_name=?, quantity=?, unit=?, kcal=?, carbs=?, protein=?, fat=?, allergens=? "
                   + "WHERE ingredient_id=?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dto.getIngredientName());
            pstmt.setDouble(2, dto.getQuantity());
            pstmt.setString(3, dto.getUnit());
            pstmt.setDouble(4, dto.getKcal());
            pstmt.setDouble(5, dto.getCarbs());
            pstmt.setDouble(6, dto.getProtein());
            pstmt.setDouble(7, dto.getFat());
            pstmt.setString(8, dto.getAllergens());
            pstmt.setInt(9, dto.getIngredientId());

            int presult = pstmt.executeUpdate();
            if (presult > 0) result = 1;

        } catch (Exception e) { e.printStackTrace(); }

        return result;
    }

    // DELETE
    public int delete(int ingredientId) {
        int result = -1;

        String sql = "DELETE FROM ingredients WHERE ingredient_id=?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ingredientId);
            int presult = pstmt.executeUpdate();

            if (presult > 0) result = 1;

        } catch (Exception e) { e.printStackTrace(); }

        return result;
    }
}
