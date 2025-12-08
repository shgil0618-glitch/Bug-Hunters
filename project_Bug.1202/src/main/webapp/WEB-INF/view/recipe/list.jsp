<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container mt-5">
    <h3>레시피 목록</h3>

    <!-- 성공 메시지 -->
    <c:if test="${not empty result}">
        <div class="alert alert-success mt-4">${result}</div>
    </c:if>

    <!-- 레시피 목록 테이블 -->
   <table class="table">
    <thead>
        <tr>
            <th>레시피 제목</th>
            <th>카테고리</th>
            <th>작성자</th>
            <th>조리 시간</th>
            <th>인분</th>
            <th>상세보기</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="recipe" items="${list}">
            <tr>
                <td>${recipe.title}</td>
                <td>${recipe.categoryName}</td> <!-- 카테고리 이름 출력 -->
                <td>${recipe.nickname}</td> <!-- 작성자 닉네임 출력 -->
                <td>${recipe.cookTime}</td>
                <td>${recipe.servings}</td>
                <td><a href="${pageContext.request.contextPath}/recipe/detail?recipeId=${recipe.recipeId}">상세보기</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>




    <!-- 페이지 네비게이션 -->
    <div class="d-flex justify-content-between">
        <button onclick="location.href='${pageContext.request.contextPath}/recipe/register'" class="btn btn-primary">레시피 등록</button>

        <!-- 페이지 네비게이션 (간단한 예시, 페이징 추가 필요시 수정) -->
        <div>
            <!-- 예시: 페이징 -->
            <ul class="pagination">
                <li class="page-item"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
            </ul>
        </div>
    </div>
</div>

<%@ include file="../inc/footer.jsp"%>
