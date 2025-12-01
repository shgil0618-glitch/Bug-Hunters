<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*" %> 
<html>
<head>
  <title>MBTI 테스트</title>
</head>
<body>
<h2>MBTI 테스트</h2>

<%
  Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");
  Statement stmt = conn.createStatement();
  ResultSet rsQuestion = stmt.executeQuery("SELECT * FROM Question ORDER BY question_id");

  while (rsQuestion.next()) {
    int questionId = rsQuestion.getInt("question_id");
    String questionText = rsQuestion.getString("text");
%>
    <form action="submitAnswer.jsp" method="post">
      <p><strong><%= questionText %></strong></p>
      <input type="hidden" name="question_id" value="<%= questionId %>">

      <%
        PreparedStatement psChoice = conn.prepareStatement("SELECT * FROM Choice WHERE question_id = ?");
        psChoice.setInt(1, questionId);
        ResultSet rsChoice = psChoice.executeQuery();

        while (rsChoice.next()) {
          int choiceId = rsChoice.getInt("choice_id");
          String choiceText = rsChoice.getString("text");
      %>
        <label>
          <input type="radio" name="choice_id" value="<%= choiceId %>" required>
          <%= choiceText %>
        </label><br>
      <%
        }
        rsChoice.close();
        psChoice.close();
      %>
      <input type="submit" value="선택">
    </form>
    <hr>
<%
  }
  rsQuestion.close();
  stmt.close();
  conn.close();
%>

<a href="mbtiResult.jsp">결과 보기</a>
</body>
</html>
