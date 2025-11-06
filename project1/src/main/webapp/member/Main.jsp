<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp" %>

<%
    String nickname = (String) session.getAttribute("nickname");
%>

<div class="container text-center mt-5">
    <p class="lead text-muted">아래 메뉴를 선택하세요</p>

    <div class="d-flex justify-content-center gap-3 mt-4 flex-wrap">
        <%
            if (nickname == null) {
        %>
            <!-- 로그인 안 된 상태 -->
            <a href="joinForm.do" class="btn btn-primary px-4 py-2">회원가입</a>
            <a href="loginForm.do" class="btn btn-success px-4 py-2">로그인</a>
        <%
            } else {
        %>
            <!-- 로그인 된 상태 -->
            <a href="mypage.do" class="btn btn-info px-4 py-2">마이페이지</a>
            <a href="logout.do" class="btn btn-danger px-4 py-2">로그아웃</a>
        <%
            }
        %>
    </div>
</div>

<%@ include file="../inc/footer.jsp" %>
