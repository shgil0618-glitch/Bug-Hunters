<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*" %> 
<html>
<head><title>MBTI 결과</title></head>
<body>
<h2>당신의 MBTI 결과는?</h2>

<%
  int appUserId = 24910; // 예시 사용자 ID

  Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");

  PreparedStatement ps = conn.prepareStatement(
    "SELECT c.mbti_type_id FROM QuestionLog ql JOIN Choice c ON ql.choice_id = c.choice_id WHERE ql.app_user_id = ?"
  );
  ps.setInt(1, appUserId);
  ResultSet rs = ps.executeQuery();

  Map<Integer, Integer> mbtiCount = new HashMap<>();
  while (rs.next()) {
    int mbtiId = rs.getInt("mbti_type_id");
    mbtiCount.put(mbtiId, mbtiCount.getOrDefault(mbtiId, 0) + 1);
  }
  rs.close();
  ps.close();

  int topMbtiId = -1;
  int maxCount = 0;
  for (Map.Entry<Integer, Integer> entry : mbtiCount.entrySet()) {
    if (entry.getValue() > maxCount) {
      topMbtiId = entry.getKey();
      maxCount = entry.getValue();
    }
  }

  if (topMbtiId != -1) {
    PreparedStatement psMbti = conn.prepareStatement("SELECT name, description FROM MbtiType WHERE mbti_type_id = ?");
    psMbti.setInt(1, topMbtiId);
    ResultSet rsMbti = psMbti.executeQuery();

    if (rsMbti.next()) {
      String mbtiName = rsMbti.getString("name");
      String mbtiDesc = rsMbti.getString("description");

      out.println("<h3>" + mbtiName + "</h3>");
      out.println("<p>" + mbtiDesc + "</p>");

      PreparedStatement psUpdate = conn.prepareStatement("UPDATE AppUser SET mbti_type_id = ? WHERE app_user_id = ?");
      psUpdate.setInt(1, topMbtiId);
      psUpdate.setInt(2, appUserId);
      psUpdate.executeUpdate();
      psUpdate.close();
    }

    rsMbti.close();
    psMbti.close();
  } else {
    out.println("<p>응답이 부족하거나 결과를 계산할 수 없습니다.</p>");
  }

  conn.close();
%>

<a href="mbtiTest.jsp">다시 테스트하기</a>
</body>
</html>
