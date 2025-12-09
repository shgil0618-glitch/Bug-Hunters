<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <title>오늘 뭐먹지?</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Bootstrap -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

  <!-- 구글 폰트 -->
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">

  <style>
    body {
      font-family: 'Noto Sans KR', sans-serif;
      background-color: #fff8eb;
    }
    .header-green {
      background-color: #4CAF50;
    }
    .logout-btn {
      width: 100%;
      text-align: left;
    }
  </style>
</head>
<body class="d-flex flex-column min-vh-100"><!-- flex 레이아웃 적용 -->

<!-- 헤더 -->
<div class="p-5 header-green text-white text-center">
  <h1>
    <a class="nav-link text-white" href="${pageContext.request.contextPath}/recipe/main">
      🍴오늘 뭐먹지?
    </a>
  </h1>
  <p>메뉴선택이 힘든자여 내게로</p>
</div>

<!-- 네비게이션 -->
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
  <div class="container-fluid">
    <ul class="navbar-nav">
      <sec:authorize access="hasRole('ROLE_ADMIN')">
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/security/admin/list">유저관리</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/recipe/list">메뉴관리</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/materiallist">재료관리</a></li>
      </sec:authorize>
      <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/recipe/main">MAIN</a></li>
    </ul>

    <ul class="navbar-nav ms-auto">
      <sec:authorize access="isAuthenticated()">
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/recipe/register">레시피 등록</a></li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="userMenu" role="button" data-bs-toggle="dropdown">
            <sec:authentication property="principal.dto.email"/>
          </a>
          <ul class="dropdown-menu dropdown-menu-end">
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/security/mypage">마이페이지</a></li>
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/recipe/mylist">My Recipe</a></li>
            <li>
              <form action="${pageContext.request.contextPath}/security/logout" method="post" class="m-0 p-0">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-outline-danger btn-sm logout-btn">로그아웃</button>
              </form>
            </li>
          </ul>
        </li>
      </sec:authorize>

      <sec:authorize access="isAnonymous()">
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/security/login">로그인</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/security/join">회원가입</a></li>
      </sec:authorize>
    </ul>
  </div>
</nav>
