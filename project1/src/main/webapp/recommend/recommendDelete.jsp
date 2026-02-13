<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<h2>추천 삭제</h2>

<form method="post">
  추천ID: <input type="number" name="recId" required>
  <input type="submit" value="삭제">
</form>

<%
String recId = request.getParameter("recId");
if(recId != null){
    Connection conn = null;
    PreparedStatement ps = null;
    try{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conn = DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:xe","yourDB","yourPW"
        );
        String sql = "DELETE FROM RECOMMEND_TB WHERE recId=?";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(recId));
        int cnt = ps.executeUpdate();
        if(cnt>0) out.println("추천 삭제 완료!");
        else out.println("삭제할 추천ID가 없습니다.");
    }catch(Exception e){ out.println("오류: " + e.getMessage()); }
    finally { if(ps!=null) ps.close(); if(conn!=null) conn.close(); }
}
%>
