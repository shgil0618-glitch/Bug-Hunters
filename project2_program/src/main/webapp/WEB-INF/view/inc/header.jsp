<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap 5 Website Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
.fakeimg {
	height: 200px;
	background: #aaa;
}
</style>
</head>
<body>

	<div class="p-5 bg-primary text-white text-center">
		<h1>MBTI Quest Board</h1>
		<p>각종 보드들의 기능익히기 - PAGING + UPLOAD + BOARD</p>
	</div>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
		<div class="container-fluid">
			<ul class="navbar-nav">
			<sec:authorize access="isAuthenticated()">
				<%-- <li class="nav-item">
	        <a class="nav-link" href="${pageContext.request.contextPath}/logout.user?appUserId=${appUserId}">로그아웃</a>
	      </li>   
	      <li class="nav-item">
	        <a class="nav-link" href="${pageContext.request.contextPath}/login.user">LOGIN</a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="${pageContext.request.contextPath}/join.user">JOIN</a>
	      </li>    
	      <li class="nav-item">
	        <a class="nav-link" href="${pageContext.request.contextPath}/mypage.user">${sessionScope.email} page </a>
	      </li>  
	      <li class="nav-item">
	        <a class="nav-link" href="${pageContext.request.contextPath}/list.user">전체회원 보기</a>
	      </li>  --%>
	      <>
				<%-- <c:choose> --%>
					<%-- 로그인 상태 --%>
					<%-- <c:when test="${not empty sessionScope.email}"> --%>
					
						<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath}/security/mypage">
								<sec:authentication  property="principal.dto.email"/> </a></li>
						<li class="nav-item">
							<form action="${pageContext.request.contextPath}/security/logout" method="post">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<input type="submit" value="로그아웃" class="btn btn-primary" />
							</form>
						</li>
						<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath}/list.user"> 유저관리 </a>
						</li>
						<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath}/list.quest"> 리스트 전체페이지 </a>
						</li>
						
						
						<%-- <li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath}/logout.user"> Logout
						</a></li>
						<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath}/list.user"> 전체페이지 </a>
						</li> --%>
					<%-- </c:when> --%>
					</sec:authorize>
					<sec:authorize access="isAnonymous()">
					<%-- 로그아웃 상태 --%>
					<%-- <c:otherwise> --%>
						<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath}/security/login"> Login </a>
						</li>
						
						<%-- <form action="${pageContext.request.contextPath}/join.user" method="post">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<input type="submit" value="join" class="btn btn-primary" />
						</form> --%>
						
						< <li class="nav-item">
						<a class="nav-link"
							href="${pageContext.request.contextPath}/security/join"> Join </a></li> 
							
							<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath}/list.user"> 유저관리 </a>
						</li>
						<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath}/list.quest"> 리스트 전체페이지 </a>
						</li>
					<%-- </c:otherwise> --%>
					</sec:authorize>

				<%-- </c:choose> --%>

			</ul>

		</div>
	</nav>
	<!-- 	header		 -->
	<!-- 	header		 -->
	<!-- 	header		 -->