<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %> 
<html>
<head><title>응답 저장</title></head>
<body>
<%
  int questionId = Integer.parseInt(request.getParameter("question_id"));
  int choiceId = Integer.parseInt(request.getParameter("choice_id"));
  int appUserId = 24910; // 예시 사용자 ID

  Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");

  PreparedStatement ps = conn.prepareStatement(
    "INSERT INTO QuestionLog (log_id, app_user_id, question_id, choice_id, timestamp) VALUES (QuestionLog_seq.NEXTVAL, ?, ?, ?, SYSDATE)"
  );
  ps.setInt(1, appUserId);
  ps.setInt(2, questionId);
  ps.setInt(3, choiceId);

  int result = ps.executeUpdate();
  if (result > 0) {
    out.println("<p>응답이 저장되었습니다!</p>");
  } else {
    out.println("<p>저장 실패. 다시 시도해주세요.</p>");
  }

  ps.close();
  conn.close();
%>

<a href="mbtiTest.jsp">다음 질문으로 이동</a> |
<a href="mbtiResult.jsp">결과 보기</a>
</body>
</html>
