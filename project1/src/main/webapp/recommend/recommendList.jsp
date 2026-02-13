<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<h2>추천 내역</h2>

<form method="get">
  사용자ID별 조회: <input type="text" name="userId">
  <input type="submit" value="조회">
</form>

<table border="1">
<tr><th>ID</th><th>음식ID</th><th>추천유형</th><th>피드백</th><th>추천일</th></tr>

<%
String userId = request.getParameter("userId");
Connection conn = null;
PreparedStatement ps = null;
ResultSet rs = null;
try{
    Class.forName("oracle.jdbc.driver.OracleDriver");
    conn = DriverManager.getConnection(
        "jdbc:oracle:thin:@localhost:1521:xe","yourDB","yourPW"
    );
    
    if(userId != null && !userId.isEmpty()){
        ps = conn.prepareStatement("SELECT * FROM RECOMMEND_TB WHERE id=?");
        ps.setString(1, userId);
    } else {
        ps = conn.prepareStatement("SELECT * FROM RECOMMEND_TB");
    }
    
    rs = ps.executeQuery();
    while(rs.next()){
%>
<tr>
    <td><%=rs.getString("id")%></td>
    <td><%=rs.getInt("foodId")%></td>
    <td><%=rs.getString("type")%></td>
    <td><%=rs.getString("feedback")%></td>
    <td><%=rs.getDate("createdAt")%></td>
</tr>
<%
    }
}catch(Exception e){ out.println("오류: " + e.getMessage()); }
finally { if(rs!=null) rs.close(); if(ps!=null) ps.close(); if(conn!=null) conn.close(); }
%>
</table>
