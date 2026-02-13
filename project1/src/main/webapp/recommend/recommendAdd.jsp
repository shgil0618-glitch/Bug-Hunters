<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<h2>추천 등록</h2>
<form method="post">
  사용자ID: <input type="text" name="userId" required><br>
  음식ID: <input type="number" name="foodId"><br>
  추천유형: 
  <select name="type">
    <option value="선호식단">선호식단</option>
    <option value="AI">AI</option>
    <option value="재료기반">재료기반</option>
  </select><br>
  <input type="submit" value="추천 등록">
</form>

<%
String userId = request.getParameter("userId");
String foodId = request.getParameter("foodId");
String type = request.getParameter("type");

if(userId != null && type != null){
    Connection conn = null;
    PreparedStatement ps = null;
    try{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conn = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe", "yourDB", "yourPW"
        );
        String sql = "INSERT INTO RECOMMEND_TB(id, foodId, type) VALUES(?,?,?)";
        ps = conn.prepareStatement(sql);
        ps.setString(1, userId);
        if(foodId != null && !foodId.isEmpty()) ps.setInt(2,Integer.parseInt(foodId));
        else ps.setNull(2, Types.INTEGER);
        ps.setString(3, type);
        ps.executeUpdate();
        out.println("추천 등록 완료!");
    }catch(Exception e){ out.println("오류: " + e.getMessage()); }
    finally { if(ps!=null) ps.close(); if(conn!=null) conn.close(); }
}
%>
