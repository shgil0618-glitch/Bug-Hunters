<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<!-- Bootstrap CSS/JS는 기존과 동일 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- Header 영역 -->
<div class="p-4 bg-primary text-white text-center">
    <h1>🍽 PROJECT1 메뉴 추천</h1>
    <p>AI 맞춤 추천과 다양한 레시피를 한 눈에!</p>
</div>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="<%=request.getContextPath()%>/">Home</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" 
            data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" 
            aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <!-- 메뉴 추천 -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" 
             data-bs-toggle="dropdown">메뉴 추천</a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="<%=request.getContextPath()%>/recommend/today.jsp">오늘의 추천</a></li>
            <li><a class="dropdown-item" href="<%=request.getContextPath()%>/recommend/ingredient.jsp">재료 기반 추천</a></li>
            <li><a class="dropdown-item" href="<%=request.getContextPath()%>/recommend/game.jsp">미니게임형 추천</a></li>
          </ul>
        </li>

        <!-- 음식 검색 -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" 
             data-bs-toggle="dropdown">음식 검색</a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="<%=request.getContextPath()%>/search/search.jsp">키워드/카테고리 검색</a></li>
            <li><a class="dropdown-item" href="<%=request.getContextPath()%>/search/detail.jsp">음식 상세보기</a></li>
          </ul>
        </li>

        <!-- 커뮤니티 -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" 
             data-bs-toggle="dropdown">커뮤니티</a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="<%=request.getContextPath()%>/list.co">레시피 공유</a></li>
            <li><a class="dropdown-item" href="<%=request.getContextPath()%>/list.co">리뷰 게시판</a></li>
          </ul>
        </li>

        <!-- 통계 대시보드 -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" 
             data-bs-toggle="dropdown">통계 대시보드</a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="<%=request.getContextPath()%>/stats/myhabit.jsp">나의 식습관 분석</a></li>
            <li><a class="dropdown-item" href="<%=request.getContextPath()%>/stats/nutrition.jsp">영양 시각화 그래프</a></li>
          </ul>
        </li>
      </ul>

      <!-- 로그인 / 마이페이지 -->
      <ul class="navbar-nav">
        <% 
            String email = (String)session.getAttribute("email");  
            Integer sid = (Integer)session.getAttribute("APP_USER_ID"); 
            if(email != null){ %>
              <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/member/mypage.jsp?APP_USER_ID=<%=sid%>">
                  <%=email%>
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/member/logout.jsp">로그아웃</a>
              </li>
        <% } else { %>
              <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/member/login.jsp">LOGIN</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/member/join.jsp">JOIN</a>
              </li>
        <% } %>
      </ul>
    </div>
  </div>
</nav>
