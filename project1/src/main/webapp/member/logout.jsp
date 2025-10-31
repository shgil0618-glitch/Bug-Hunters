<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	session.invalidate();   // 서버에 저장되어 있는기록 싹다 지우세요
	out.println("<script> alert('로그아웃! 다음에 다시만나요~'); location.href='login.jsp';  </script>");
%>